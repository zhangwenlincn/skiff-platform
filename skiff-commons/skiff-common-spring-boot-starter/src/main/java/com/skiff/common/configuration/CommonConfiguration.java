package com.skiff.common.configuration;

import com.skiff.common.exception.GlobalExceptionHandler;
import com.skiff.common.interceptor.ThreadLocalInterceptor;
import com.skiff.common.util.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({GlobalExceptionHandler.class, SpringUtil.class})
public class CommonConfiguration implements WebMvcConfigurer {

    @Bean
    public ThreadLocalInterceptor threadLocalInterceptor() {
        return new ThreadLocalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(threadLocalInterceptor())
                .addPathPatterns("/**");
    }
}
