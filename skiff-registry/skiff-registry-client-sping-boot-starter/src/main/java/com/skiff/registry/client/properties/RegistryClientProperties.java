package com.skiff.registry.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "skiff.registry.client")
public class RegistryClientProperties {

    private Integer port = 50051;

    private Registry registry = new Registry();

    @Data
    public static class Registry {
        private String ip = "127.0.0.1";
        private Integer port = 50050;
    }
}
