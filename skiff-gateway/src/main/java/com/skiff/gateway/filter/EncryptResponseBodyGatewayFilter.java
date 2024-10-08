package com.skiff.gateway.filter;

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

import java.util.Map;


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

        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        String appKey = exchange.getRequest().getQueryParams().getFirst("appKey");
        boolean isApplicationJson = contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON);
        if (!isApplicationJson) {
            log.debug("response is not application/json content type, skip filter");
            return chain.filter(exchange);
        }

        final ModifyResponseBodyGatewayFilterFactory.Config config = new ModifyResponseBodyGatewayFilterFactory.Config();
        config.setRewriteFunction(String.class, String.class, (serverWebExchange, body) -> {
            RequestPath path = exchange.getRequest().getPath();
            if (decryptionEncryptionProperties.getResponse().getUrls().contains(path.value())) {
                Map<String, Object> map = JsonUtil.toMap(body);
                if (map.containsKey("success") && map.get("success") instanceof Boolean && (Boolean) map.get("success")) {
                    Object data = map.get("data");
                    if (data != null) {
                        try {
                            String encrypt = AESUtil.encrypt(JsonUtil.toJson(data), AESUtil.decodeKeyFromBase64(keySecretService.getSecretByKey(appKey)));
                            map.put("data", encrypt);
                            body = JsonUtil.toJson(map);
                        } catch (Exception e) {
                            throw new SkiffException(BaseCodeEnum.AES_DECRYPT_FAIL);
                        }
                    }
                }
            }
            return Mono.just(body);
        });
        return modifyResponseBodyFilterFactory.apply(config).filter(exchange, chain);
    }

}
