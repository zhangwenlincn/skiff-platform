package com.skiff.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.util.AESUtil;
import com.skiff.common.core.util.JsonUtil;
import com.skiff.gateway.properties.DecryptionEncryptionProperties;
import com.skiff.gateway.service.KeySecretService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Order(-1)
public class EncryptResponseBodyGatewayFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(EncryptResponseBodyGatewayFilter.class);

    @Resource
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilterFactory;

    @Resource
    private DecryptionEncryptionProperties decryptionEncryptionProperties;
    @Resource
    private KeySecretService keySecretService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        RequestPath path = exchange.getRequest().getPath();
        if (decryptionEncryptionProperties.getResponse().getUrls().contains(path.value())) {
            String appKey = exchange.getRequest().getQueryParams().getFirst("appKey");
            final ModifyResponseBodyGatewayFilterFactory.Config config = new ModifyResponseBodyGatewayFilterFactory.Config();
            config.setRewriteFunction(String.class, String.class, (serverWebExchange, body) -> {
                MediaType responseContentType = serverWebExchange.getResponse().getHeaders().getContentType();
                boolean isApplicationJson = responseContentType != null && responseContentType.isCompatibleWith(MediaType.APPLICATION_JSON);
                if (isApplicationJson) {
                    JsonNode node = JsonUtil.toJsonNode(body);
                    if (node.get("success").asBoolean(false) && node.has("data")) {
                        try {
                            JsonNode data = node.get("data");
                            String encrypt = AESUtil.encrypt(JsonUtil.toJson(data), AESUtil.decodeKeyFromBase64(keySecretService.getSecretByKey(appKey)));
                            ObjectNode objectNode = (ObjectNode) node;
                            objectNode.put("data", encrypt);
                            body = JsonUtil.toJson(objectNode);
                        } catch (Exception e) {
                            throw new SkiffException(BaseCodeEnum.AES_DECRYPT_FAIL);
                        }
                    }
                    return Mono.just(body);
                } else {
                    return Mono.empty();
                }
            });
            return modifyResponseBodyFilterFactory.apply(config).filter(exchange, chain);
        } else {
            return chain.filter(exchange);
        }
    }

}
