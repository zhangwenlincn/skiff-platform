package skiff.message.core.timer;

import com.skiff.common.core.result.BaseResult;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import skiff.message.core.annotation.MessageEntity;
import skiff.message.core.base.Actuator;
import skiff.message.core.base.Message;
import skiff.message.core.helper.MessageActuatorHelper;
import skiff.message.core.storage.MessageStorageService;


@SuppressWarnings("all")
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
        try {
            boolean lock = messageStorageService.tryLockMessage(message);
            if (lock) {
                actuate();
            } else {
                logger.debug("Message lock failed, message id: {}, message: {}", message.getId(), message);
            }
        } catch (Exception e) {
            logger.error("Message actuate failed, message id: {}, message: {}", message.getId(), message, e);
        } finally {
            messageStorageService.unlockMessage(message);
            messageStorageService.deleteMessage(message.getId());
        }
    }

    private void actuate() {
        if (message.getClass().isAnnotationPresent(MessageEntity.class)) {
            MessageEntity messageEntity = message.getClass().getAnnotation(MessageEntity.class);
            String messageEntityKey = messageEntity.group() + ":" + messageEntity.topic();
            Actuator actuator = MessageActuatorHelper.get(messageEntityKey);

            int retryCount = message.getRetryTimes();
            boolean success = false;
            BaseResult actuateResult = null;

            String errorMessage = null;
            for (int i = 0; i < retryCount; i++) {
                try {
                    actuateResult = actuator.actuate(message);
                    if (actuateResult.isSuccess()) {
                        success = true;
                        break;
                    }
                    errorMessage = actuateResult.getMessage();
                    logger.error("Message actuator failed, retry count: {}, MessageEntity:{} message id: {}, message: {}, error: {}", i + 1, messageEntityKey, message.getId(), message, actuateResult.getMessage());
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    logger.error("Message actuator failed with exception, retry count: {}, MessageEntity:{} message id: {}, message: {}", i + 1, messageEntityKey, message.getId(), message, e);
                }
            }
            if (success) {
                logger.debug("Message actuator success, MessageEntity:{} message id: {}, message: {}", messageEntityKey, message.getId(), message);
            } else {
                messageStorageService.errorMessage(message, errorMessage);

                logger.error("Message actuator failed after retries, MessageEntity:{} message id: {}, message: {}, error: {}", messageEntityKey, message.getId(), message, actuateResult != null ? actuateResult.getMessage() : "Unknown error");
            }
        } else {
            logger.error("Message actuator not found, message id: {}, message: {}", message.getId(), message);
        }
    }

}
