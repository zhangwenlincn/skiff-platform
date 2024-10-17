package com.skiff.app.test.message;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skiff.message.core.annotation.MessageActuator;
import skiff.message.core.base.Actuator;

@Slf4j
@Component
@MessageActuator(group = "order", topic = "order")
public class OrderActuator implements Actuator<OrderMessage> {

    @Resource
    private TestComponent testComponent;

    @Override
    public BaseResult actuate(OrderMessage message) {
        log.info(message.getMessage());
        testComponent.sayHello();
        return Results.baseResult(true);
    }
}
