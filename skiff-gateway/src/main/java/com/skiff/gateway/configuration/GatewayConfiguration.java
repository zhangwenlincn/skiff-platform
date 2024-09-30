package com.skiff.gateway.configuration;

import com.skiff.gateway.filter.RequestDecryptionFilter;
import com.skiff.gateway.properties.DecryptionEncryptionProperties;
import com.skiff.gateway.service.KeySecretService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RequestDecryptionFilter requestDecryptionFilter() {
        return new RequestDecryptionFilter();
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
