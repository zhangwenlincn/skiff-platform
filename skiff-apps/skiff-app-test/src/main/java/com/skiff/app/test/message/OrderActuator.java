package com.skiff.app.test.message;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.common.core.util.JsonUtil;
import com.skiff.common.message.annotation.MessageActuator;
import com.skiff.common.message.core.Actuator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@MessageActuator(group = "order", topic = "order")
public class OrderActuator implements Actuator<OrderMessage> {

    @Resource
    private TestComponent testComponent;

    @Override
    public BaseResult actuate(OrderMessage message) {
        testComponent.sayHello();
        System.out.println(Thread.currentThread().getName() + "Order Actuator received message: " + JsonUtil.toJson(message) + System.currentTimeMillis());
        if (Objects.equals(message.getOrderId(), "2")){
            System.out.println("Order 2 is not available");
            throw new RuntimeException("Order 2 is not available");
        }
        if (Objects.equals(message.getOrderId(), "3")){
            return Results.baseResult( "Order 3 is not available");
        }
        return Results.baseResult(true);
    }
}
