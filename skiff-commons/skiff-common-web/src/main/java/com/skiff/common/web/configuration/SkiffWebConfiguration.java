package com.skiff.common.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiff.common.core.util.JsonUtil;
import com.skiff.common.web.exception.SkiffExceptionHandler;
import com.skiff.common.web.filter.RequestResponseFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
public class SkiffWebConfiguration implements WebMvcConfigurer {

    @Bean
    public SkiffExceptionHandler skiffExceptionHandler() {
        return new SkiffExceptionHandler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtil.getObjectMapper();
    }

    @Bean
    public RequestResponseFilter requestResponseFilter() {
        return new RequestResponseFilter();
    }
}
