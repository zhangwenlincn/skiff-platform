package com.skiff.common.message.test;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.common.core.util.JsonUtil;
import com.skiff.common.message.annotation.MessageActuator;
import com.skiff.common.message.core.Actuator;

@MessageActuator(group = "order", topic = "order")
public class OrderActuator implements Actuator<OrderMessage> {
    @Override
    public BaseResult actuate(OrderMessage message) {
        System.out.println(Thread.currentThread().getName() + "Order Actuator received message: " + JsonUtil.toJson(message) + System.currentTimeMillis());
        return Results.baseResult(true);
    }
}
