package com.skiff.app.test.message;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.common.message.annotation.MessageActuator;
import com.skiff.common.message.core.Actuator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
@MessageActuator(group = "order", topic = "order")
public class OrderActuator implements Actuator<OrderMessage> {

    @Resource
    private TestComponent testComponent;

    @Override
    public BaseResult actuate(OrderMessage message) {
        testComponent.sayHello();
        return Results.baseResult(true);
    }
}
