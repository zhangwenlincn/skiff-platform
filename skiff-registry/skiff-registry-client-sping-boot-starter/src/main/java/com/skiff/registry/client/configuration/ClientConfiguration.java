package com.skiff.registry.client.configuration;

import com.skiff.registry.client.grpc.CallbackServer;
import com.skiff.registry.client.properties.RegistryClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.skiff.registry.client")
@EnableConfigurationProperties(RegistryClientProperties.class)
public class ClientConfiguration {

    private final RegistryClientProperties registryClientProperties;

    public ClientConfiguration(RegistryClientProperties registryClientProperties) {
        this.registryClientProperties = registryClientProperties;
    }

    @Bean
    public CallbackServer callbackServer() {
        CallbackServer callbackServer = new CallbackServer(registryClientProperties.getPort());
        callbackServer.start();
        return callbackServer;
    }
}
