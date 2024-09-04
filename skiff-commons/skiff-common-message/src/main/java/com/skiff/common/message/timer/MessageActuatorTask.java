package com.skiff.common.message.timer;

import com.skiff.common.message.core.Message;

public interface MessageActuatorTask {


    /**
     * This method is called when the message is received by the actuator manually.
     *
     * @param message the message received by the actuator.
     */
    default void manual(Message message) {
        // do nothing by default
    }

    /**
     * This method is called when the message is received by the actuator automatically.
     *
     * @param message the message received by the actuator.
     */
    default void automatic(Message message) {
        // do nothing by default
    }

    /**
     * This method is called when the message is received by the actuator automatically.
     */
    void schedule();


}
