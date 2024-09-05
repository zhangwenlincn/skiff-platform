package com.skiff.common.message.timer;

import com.skiff.common.message.core.Message;
import com.skiff.common.message.core.MessageTimeout;
import com.skiff.common.message.helper.MessageTimeoutHelper;
import com.skiff.common.message.storage.MessageStorageService;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DefaultMessageActuatorTask implements MessageActuatorTask {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageActuatorTask.class);

    private final HashedWheelTimer run;

    private final MessageStorageService messageStorageService;

    public DefaultMessageActuatorTask(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
        this.run = new HashedWheelTimer(new DefaultThreadFactory("skiff-message-task-run"), 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void manual(Message message) {
        Timeout timeout = run.newTimeout(new MessageActuatorTimer(message), message.getExecuteTimestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        timeout.task();
        MessageTimeoutHelper.add(message.getId(), new MessageTimeout(message.getExecuteTimestamp(), timeout));
    }

    @Override
    public void automatic(Message message) {
        Timeout timeout = run.newTimeout(new MessageActuatorTimer(messageStorageService, message), message.getExecuteTimestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        timeout.task();
        MessageTimeoutHelper.add(message.getId(), new MessageTimeout(message.getExecuteTimestamp(), timeout));
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
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (Exception e) {
                    logger.error("DefaultMessageActuatorTask schedule error", e);
                }
            }
        }).start();
    }

}
