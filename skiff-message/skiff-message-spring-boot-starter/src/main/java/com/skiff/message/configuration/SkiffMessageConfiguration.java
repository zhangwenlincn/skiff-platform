package com.skiff.message.configuration;

import com.skiff.message.annotation.EnableMessage;
import com.skiff.message.register.SpringMessageActuatorRegister;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import skiff.message.core.storage.MessageStorageService;
import skiff.message.core.timer.DefaultMessageActuatorTask;
import skiff.message.core.timer.MessageActuatorTask;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class SkiffMessageConfiguration implements ApplicationContextAware {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SkiffMessageConfiguration.class);
    private final MessageStorageService messageStorageService;
    private ApplicationContext applicationContext;

    public SkiffMessageConfiguration(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(initMethod = "schedule")
    public MessageActuatorTask defaultMessageActuatorTask() {
        SpringMessageActuatorRegister register = new SpringMessageActuatorRegister(applicationContext);
        Map<String, Object> startMap = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        List<String> packages = startMap.values().stream().map(x -> x.getClass().getPackage().getName()).collect(Collectors.toList());
        Map<String, Object> messageDefultMap = applicationContext.getBeansWithAnnotation(EnableMessage.class);
        if (!messageDefultMap.isEmpty()) {
            packages.addAll(messageDefultMap.values().stream().map(x -> x.getClass().getPackage().getName()).toList());
            packages.addAll(messageDefultMap.values().stream().map(x -> {
                EnableMessage annotation = x.getClass().getAnnotation(EnableMessage.class);
                return Arrays.stream(annotation.scanBasePackages()).toList();
            }).flatMap(Collection::stream).distinct().filter(Objects::nonNull).toList());
        }
        List<String> actualPackages = packages.stream().distinct().collect(Collectors.toList());
        register.register(actualPackages.toArray(new String[0]));
        logger.info("Message actuator register completed. register packages: {}", actualPackages);
        return new DefaultMessageActuatorTask(messageStorageService);
    }


}
