package com.skiff.gateway.app.configuration;

import com.skiff.gateway.app.filter.DecryptRequestBodyGatewayFilter;
import com.skiff.gateway.app.filter.EncryptResponseBodyGatewayFilter;
import com.skiff.gateway.app.properties.DecryptionEncryptionProperties;
import com.skiff.gateway.app.service.KeySecretService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DecryptionEncryptionProperties.class)
public class GatewayConfiguration {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GatewayConfiguration.class);

    @Bean
    @ConditionalOnProperty(name = "skiff.gateway.request.enabled")
    public DecryptRequestBodyGatewayFilter modifyRequestBodyGatewayFilter() {
        return new DecryptRequestBodyGatewayFilter();
    }

    @Bean
    @ConditionalOnProperty(name = "skiff.gateway.response.enabled")
    public EncryptResponseBodyGatewayFilter encryptResponseBodyGatewayFilter() {
        return new EncryptResponseBodyGatewayFilter();
    }

    @Bean
    public KeySecretService testKeySecretService() {
        return key -> "zrZJA8OF636DIONtmBhqmxtV90fQmYeUSc1frOMFcSg=";
    }

}
