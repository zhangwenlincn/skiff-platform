package com.skiff.registry.server.configuration;

import com.skiff.registry.server.grpc.RegistryServer;
import com.skiff.registry.server.properties.RegistryServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.skiff.registry.server")
@EnableConfigurationProperties(RegistryServerProperties.class)
public class ServerConfiguration {


    private final RegistryServerProperties registryServerProperties;

    public ServerConfiguration(RegistryServerProperties registryServerProperties) {
        this.registryServerProperties = registryServerProperties;
    }

    @Bean
    public RegistryServer registryServer() {
        RegistryServer registryServer = new RegistryServer(registryServerProperties.getPort());
        registryServer.start();
        return registryServer;
    }
}
