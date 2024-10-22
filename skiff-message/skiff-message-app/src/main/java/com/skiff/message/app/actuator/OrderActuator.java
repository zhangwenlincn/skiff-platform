package com.skiff.message.app.actuator;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.message.app.message.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import skiff.message.core.annotation.MessageActuator;
import skiff.message.core.base.Actuator;

@RequiredArgsConstructor
@Component
@MessageActuator(group = "order", topic = "order")
public class OrderActuator implements Actuator<OrderMessage> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OrderActuator.class);

    @Override
    public BaseResult actuate(OrderMessage message) {
        logger.info("OrderActuator actuate message: {}", message);
        return Results.baseResult(true);
    }
}
