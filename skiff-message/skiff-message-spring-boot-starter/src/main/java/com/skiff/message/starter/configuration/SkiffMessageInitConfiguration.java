package com.skiff.message.starter.configuration;

import com.skiff.message.starter.properties.MessageStorageProperties;
import com.skiff.message.starter.storage.MemoryMessageStorage;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skiff.message.core.storage.MessageStorageService;

@Configuration
@AutoConfigureBefore({SkiffMessageConfiguration.class})
public class SkiffMessageInitConfiguration {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SkiffMessageInitConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "skiff.message.storage", name = "type", havingValue = "memory", matchIfMissing = true)
    public MessageStorageService messageStorageService() {
        logger.info("Message storage type is memory");
        return new MemoryMessageStorage();
    }

}
