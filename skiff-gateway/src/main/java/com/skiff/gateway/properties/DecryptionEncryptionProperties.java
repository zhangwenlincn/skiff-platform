package com.skiff.gateway.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "skiff.cloud.gateway")
public class DecryptionEncryptionProperties {


    /**
     * Decryption keys.
     */
    private List<String> decryption;

    /**
     * Encryption keys.
     */
    private List<String> encryption;
}
