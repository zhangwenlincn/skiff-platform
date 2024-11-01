package com.skiff.registry.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@Data
@ConfigurationProperties(prefix = "skiff.registry.server")
public class RegistryServerProperties {

    private Integer port = 50050;

    private Registered registered;

    public Registered getRegistered() {
        return Optional.ofNullable(registered).orElse(Registered.def);
    }

    public enum Registered {
        def;
    }
}
