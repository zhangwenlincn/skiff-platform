package skiff.message.core.timer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skiff.message.core.base.Message;
import skiff.message.core.base.MessageTimeout;
import skiff.message.core.helper.MessageTimeoutHelper;
import skiff.message.core.storage.MessageStorageService;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DefaultMessageActuatorTask implements MessageActuatorTask {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageActuatorTask.class);

    private final HashedWheelTimer run;

    private final MessageStorageService messageStorageService;

    // scan message interval, default interval is 100ms
    private Long interval = 100L;


    public DefaultMessageActuatorTask(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
        this.run = new HashedWheelTimer(new DefaultThreadFactory("skiff-message-task-run"), 100, TimeUnit.MILLISECONDS);
    }

    public DefaultMessageActuatorTask(MessageStorageService messageStorageService, Long interval) {
        this.messageStorageService = messageStorageService;
        this.run = new HashedWheelTimer(new DefaultThreadFactory("skiff-message-task-run"), 100, TimeUnit.MILLISECONDS);
        this.interval = interval;
    }

    public DefaultMessageActuatorTask(MessageStorageService messageStorageService, Long interval, Long tickDuration) {
        this.messageStorageService = messageStorageService;
        this.run = new HashedWheelTimer(new DefaultThreadFactory("skiff-message-task-run"), tickDuration, TimeUnit.MILLISECONDS);
        this.interval = interval;
    }

    @Override
    public void manual(Message message) {
        Timeout timeout = run.newTimeout(new MessageActuatorTimer(message), message.getExecuteTimestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        timeout.task();
        MessageTimeoutHelper.add(message.getId(), new MessageTimeout(message.getExecuteTimestamp() + 10 * 1000, timeout));
    }

    @Override
    public void automatic(Message message) {
        Timeout timeout = run.newTimeout(new MessageActuatorTimer(messageStorageService, message), message.getExecuteTimestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        timeout.task();
        MessageTimeoutHelper.add(message.getId(), new MessageTimeout(message.getExecuteTimestamp() + 10 * 1000, timeout));
    }

    @Override
    public void schedule() {
        new Thread(() -> {
            while (true) {
                try {
                    Set<String> effective = MessageTimeoutHelper.effective();
                    messageStorageService.getMessages().forEach(message -> {
                        if (!effective.contains(message.getId())) {
                            automatic(message);
                        }
                    });
                    TimeUnit.MILLISECONDS.sleep(interval);
                } catch (Exception e) {
                    logger.error("DefaultMessageActuatorTask schedule error", e);
                }
            }
        }).start();
    }

}
