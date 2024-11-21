package com.skiff.message.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "skiff.message.storage")
public class MessageStorageProperties {

    private StorageType type;


    enum StorageType {

        MEMORY, REDIS, CUSTOM
    }
}
