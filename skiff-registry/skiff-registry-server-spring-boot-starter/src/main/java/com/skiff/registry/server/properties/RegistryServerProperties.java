package com.skiff.registry.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "skiff.registry.server")
public class RegistryServerProperties {

    private Integer port = 50050;

    private Registered registered = Registered.def;

    public enum Registered {
        def;
    }
}
