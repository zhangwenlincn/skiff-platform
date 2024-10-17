package com.skiff.gateway.app.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "skiff.gateway")
public class DecryptionEncryptionProperties {


    /**
     * Decryption keys.
     */
    private RequestDecryption request;

    /**
     * Encryption keys.
     */
    private ResponseEncryption response;


    @Data
    public static class RequestDecryption {
        private boolean enabled;
        private List<String> urls;
    }

    @Data
    public static class ResponseEncryption {
        private boolean enabled;
        private List<String> urls;
    }

}
