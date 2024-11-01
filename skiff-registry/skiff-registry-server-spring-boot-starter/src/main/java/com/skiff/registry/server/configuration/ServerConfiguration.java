package com.skiff.registry.server.configuration;

import com.skiff.registry.server.grpc.RegistryServer;
import com.skiff.registry.server.properties.RegistryServerProperties;
import com.skiff.registry.server.service.DefaultRegisteredServiceImpl;
import com.skiff.registry.server.service.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.skiff.registry.server")
@EnableConfigurationProperties(RegistryServerProperties.class)
public class ServerConfiguration {


    private static final Logger log = LoggerFactory.getLogger(ServerConfiguration.class);
    private final RegistryServerProperties registryServerProperties;

    public ServerConfiguration(RegistryServerProperties registryServerProperties) {
        this.registryServerProperties = registryServerProperties;
    }

    @Bean(initMethod = "start")
    public RegistryServer registryServer() {
        return new RegistryServer(registryServerProperties.getPort());
    }

    @Bean
    @ConditionalOnProperty(prefix = "skiff.registry.server", name = "registered", havingValue = "def", matchIfMissing = true)
    public RegisteredService registeredService() {
        log.info("skiff.registry.server.registered = def");
        return new DefaultRegisteredServiceImpl();
    }
}
