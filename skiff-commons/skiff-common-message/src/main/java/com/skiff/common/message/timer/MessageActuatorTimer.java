package com.skiff.common.message.timer;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.message.annotation.MessageActuator;
import com.skiff.common.message.annotation.MessageEntity;
import com.skiff.common.message.context.MessageActuatorHelper;
import com.skiff.common.message.core.Actuator;
import com.skiff.common.message.core.Message;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;

public class MessageActuatorTimer implements TimerTask {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageActuatorTimer.class);

    private final Message message;


    public MessageActuatorTimer(Message message) {
        this.message = message;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        if (message.getClass().isAnnotationPresent(MessageActuator.class)) {
            MessageEntity messageEntity = message.getClass().getAnnotation(MessageEntity.class);
            String messageEntityKey = messageEntity.group() + ":" + messageEntity.topic();
            Actuator actuator = MessageActuatorHelper.get(messageEntityKey);
            BaseResult actuateResult = actuator.actuate(message);
            if (actuateResult.isSuccess()) {
                logger.debug("Message actuator success, MessageEntity:{} message id: {}, message: {}", messageEntityKey, message.getId(), message);
            } else {
                logger.error("Message actuator failed, MessageEntity:{} message id: {}, message: {}, error: {}", messageEntityKey, message.getId(), message, actuateResult.getMessage());
            }
        } else {
            logger.error("Message actuator not found, message id: {}, message: {}", message.getId(), message);
        }
    }
}
