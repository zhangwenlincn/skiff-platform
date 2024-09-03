package com.skiff.common.message.timer;

import com.skiff.common.message.core.Message;

public interface MessageActuatorTask {

    default void manual(Message message) {
        // do nothing by default
    }


}
