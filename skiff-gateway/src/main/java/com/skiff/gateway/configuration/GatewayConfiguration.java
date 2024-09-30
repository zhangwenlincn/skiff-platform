package com.skiff.gateway.configuration;

import com.skiff.gateway.filter.RequestDecryptionFilter;
import com.skiff.gateway.filter.ResponseEncryptionFilter;
import com.skiff.gateway.properties.DecryptionEncryptionProperties;
import com.skiff.gateway.service.KeySecretService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GatewayConfiguration.class);

    @Bean
    @ConditionalOnProperty(name = "skiff.gateway.request.enabled")
    public RequestDecryptionFilter requestDecryptionFilter() {
        log.debug("request body decryption is enabled");
        return new RequestDecryptionFilter();
    }

    @Bean
    @ConditionalOnProperty(name = "skiff.gateway.response.enabled")
    public ResponseEncryptionFilter responseEncryptionFilter() {
        log.debug("response body encryption is enabled");
        return new ResponseEncryptionFilter();
    }


    @Bean
    public DecryptionEncryptionProperties decryptionEncryptionProperties() {
        return new DecryptionEncryptionProperties();
    }


    @Bean
    public KeySecretService testKeySecretService() {
        return key -> "zrZJA8OF636DIONtmBhqmxtV90fQmYeUSc1frOMFcSg=";
    }

}
