package com.skiff.gateway.filter;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.util.AESUtil;
import com.skiff.gateway.properties.DecryptionEncryptionProperties;
import com.skiff.gateway.service.KeySecretService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;


public class RequestDecryptionGlobalFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestDecryptionGlobalFilter.class);

    @Resource
    private DecryptionEncryptionProperties decryptionEncryptionProperties;

    @Resource
    private KeySecretService keySecretService;
    //实际请求地址
    private final static String gatewayOriginalRequestUrl = "org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayOriginalRequestUrl";
    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String appKey = request.getQueryParams().getFirst("appKey");
        log.debug("appKey: {}", appKey);
        String appSecret = Optional.ofNullable(keySecretService.getSecretByKey(appKey))
                .orElseThrow(() -> new SkiffException(BaseCodeEnum.APP_NOT_FOUND));

        LinkedHashSet<URI> originalRequestUrls = exchange.getRequiredAttribute(gatewayOriginalRequestUrl);
        URI uri = originalRequestUrls.getFirst();
        log.debug("Original gateway request url: {}", uri);
        String path = uri.getPath();
        if (!decryptionEncryptionProperties.getDecryption().contains(path)) {
            log.debug("Request path {} is not in decryption list, skip filter", path);
            return chain.filter(exchange);
        }

        //application/json
        MediaType contentType = request.getHeaders().getContentType();
        boolean isApplicationJson = contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON);
        if (!isApplicationJson || request.getMethod() != HttpMethod.POST) {
            log.debug("Request is not application/json or method is not POST, skip filter");
            return chain.filter(exchange);
        }

        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = decryptionBody(serverRequest, appSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    SkiffServerHttpRequestDecorator decorator = new SkiffServerHttpRequestDecorator(request, outputMessage);
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }

    private static BodyInserter<Mono<String>, ReactiveHttpOutputMessage> decryptionBody(ServerRequest serverRequest, String appSecret) {
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .flatMap(body -> {
                    try {
                        String newBody = AESUtil.decrypt(body, AESUtil.decodeKeyFromBase64(appSecret));
                        return Mono.just(newBody);
                    } catch (Exception e) {
                        return Mono.error(new SkiffException(BaseCodeEnum.AES_DECRYPT_FAIL));
                    }
                });
        return BodyInserters.fromPublisher(modifiedBody, String.class);
    }

    @SuppressWarnings("all")
    private static class SkiffServerHttpRequestDecorator extends ServerHttpRequestDecorator {
        private final CachedBodyOutputMessage outputMessage;

        public SkiffServerHttpRequestDecorator(ServerHttpRequest delegate, CachedBodyOutputMessage outputMessage) {
            super(delegate);
            this.outputMessage = outputMessage;
        }

        @Override
        public HttpHeaders getHeaders() {
            return outputMessage.getHeaders();
        }

        @Override
        public Flux<DataBuffer> getBody() {
            return outputMessage.getBody();
        }
    }
}


