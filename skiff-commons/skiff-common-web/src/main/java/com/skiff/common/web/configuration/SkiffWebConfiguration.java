package com.skiff.common.web.configuration;

import com.skiff.common.web.exception.SkiffExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class SkiffWebConfiguration {

    @Bean
    public SkiffExceptionHandler skiffExceptionHandler() {
        return new SkiffExceptionHandler();
    }
}
