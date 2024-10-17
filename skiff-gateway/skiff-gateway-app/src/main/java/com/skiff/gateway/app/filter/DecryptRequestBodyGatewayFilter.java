package com.skiff.gateway.app.filter;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.util.AESUtil;
import com.skiff.gateway.app.properties.DecryptionEncryptionProperties;
import com.skiff.gateway.app.service.KeySecretService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(0)
public class DecryptRequestBodyGatewayFilter implements GlobalFilter {
    private static final Logger log = LoggerFactory.getLogger(DecryptRequestBodyGatewayFilter.class);
    @Resource
    private ModifyRequestBodyGatewayFilterFactory factory;
    @Resource
    private DecryptionEncryptionProperties decryptionEncryptionProperties;
    @Resource
    private KeySecretService keySecretService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        String appKey = exchange.getRequest().getQueryParams().getFirst("appKey");
        boolean isApplicationJson = contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON);
        if (!isApplicationJson) {
            log.debug("Request is not application/json content type, skip filter");
            return chain.filter(exchange);
        }
        ModifyRequestBodyGatewayFilterFactory.Config config = new ModifyRequestBodyGatewayFilterFactory.Config();
        config.setRewriteFunction(String.class, String.class, (serverWebExchange, body) -> {
            RequestPath path = exchange.getRequest().getPath();
            log.debug("path: {}", path.value());
            if (decryptionEncryptionProperties.getRequest().getUrls().contains(path.value())) {
                try {
                    body = AESUtil.decrypt(body, AESUtil.decodeKeyFromBase64(keySecretService.getSecretByKey(appKey)));
                } catch (Exception e) {
                    log.error("AES decrypt fail", e);
                    throw new SkiffException(BaseCodeEnum.AES_DECRYPT_FAIL);
                }
            }
            return Mono.just(body);
        });
        return factory.apply(config).filter(exchange, chain);
    }
}
