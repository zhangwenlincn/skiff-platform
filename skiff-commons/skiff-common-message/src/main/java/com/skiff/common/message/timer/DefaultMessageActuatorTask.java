package com.skiff.common.message.timer;

import com.skiff.common.message.core.Message;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.TimeUnit;

public class DefaultMessageActuatorTask implements MessageActuatorTask {

    private final HashedWheelTimer run;


    public DefaultMessageActuatorTask() {
        this.run = new HashedWheelTimer(new DefaultThreadFactory("skiff-message-task-run"), 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void manual(Message message) {
        Timeout timeout = run.newTimeout(new MessageActuatorTimer(message), message.getExecuteTimestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        timeout.task();
    }
}
