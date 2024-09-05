package com.skiff.common.message.timer;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.message.annotation.MessageEntity;
import com.skiff.common.message.core.Actuator;
import com.skiff.common.message.core.Message;
import com.skiff.common.message.helper.MessageActuatorHelper;
import com.skiff.common.message.storage.MessageStorageService;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;

public class MessageActuatorTimer implements TimerTask {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageActuatorTimer.class);

    private final Message message;

    private MessageStorageService messageStorageService;


    public MessageActuatorTimer(Message message) {
        this.message = message;
    }

    public MessageActuatorTimer(MessageStorageService messageStorageService, Message message) {
        this.message = message;
        this.messageStorageService = messageStorageService;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        //微服务 锁住消息，防止重复执行

        if (messageStorageService == null) {
            logger.debug("Message storage service not found");
            actuate();
        } else {
            boolean lock = messageStorageService.tryLockMessage(message);
            if (lock) {
                actuate();
            } else {
                logger.debug("Message lock failed, message id: {}, message: {}", message.getId(), message);
            }
        }


    }

    private void actuate() {
        try {
            if (message.getClass().isAnnotationPresent(MessageEntity.class)) {
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
        } catch (Exception e) {
            logger.error("Message actuator failed, message id: {}, message: {}, error: ", message.getId(), message, e);
        } finally {
            messageStorageService.deleteMessage(message.getId());
        }

    }
}
