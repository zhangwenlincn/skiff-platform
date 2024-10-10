package com.skiff.starter.message.register;

import com.skiff.common.message.annotation.MessageActuator;
import com.skiff.common.message.core.Actuator;
import com.skiff.common.message.helper.MessageActuatorHelper;
import com.skiff.common.message.register.MessageActuatorRegister;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpringMessageActuatorRegister implements MessageActuatorRegister {

    private final ApplicationContext applicationContext;

    public SpringMessageActuatorRegister(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void register(String... packages) {

        if (packages == null || packages.length == 0) {
            throw new IllegalArgumentException("packages must not be null or empty");
        }
        List<String> packageList = Arrays.stream(packages).toList();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(MessageActuator.class);
        beansWithAnnotation.forEach((name, bean) -> {
            String packageName = bean.getClass().getPackageName();
            if (packageList.stream().anyMatch(packageName::startsWith)) {
                MessageActuator annotation = bean.getClass().getAnnotation(MessageActuator.class);
                MessageActuatorHelper.add(annotation.group() + ":" + annotation.topic(), (Actuator<?>) bean);
            }
        });
    }


}
