package com.skiff.starter.message.configuration;

import com.skiff.common.message.register.MessageActuatorRegister;
import com.skiff.common.message.storage.MessageStorageService;
import com.skiff.common.message.timer.DefaultMessageActuatorTask;
import com.skiff.common.message.timer.MessageActuatorTask;
import com.skiff.starter.message.register.SpringMessageActuatorRegister;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SkiffMessageConfiguration implements ApplicationContextAware {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SkiffMessageConfiguration.class);

    private ApplicationContext applicationContext;

    private final MessageStorageService messageStorageService;

    public SkiffMessageConfiguration(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public MessageActuatorTask defaultMessageActuatorTask() {
        SpringMessageActuatorRegister register = new SpringMessageActuatorRegister(applicationContext);
        Map<String, Object> startMap = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        List<String> packages = startMap.values().stream().map(x -> x.getClass().getPackage().getName()).toList();
        register.register(packages.toArray(new String[0]));
        logger.info("Message actuator register completed. register packages: {}", packages);
        MessageActuatorTask task = new DefaultMessageActuatorTask(messageStorageService);
        task.schedule();
        return task;
    }
}
