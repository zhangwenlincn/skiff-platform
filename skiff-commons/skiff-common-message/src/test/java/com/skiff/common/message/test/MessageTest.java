package com.skiff.common.message.test;

import com.skiff.common.message.register.DefaultMessageActuatorRegister;
import com.skiff.common.message.storage.MessageStorageService;
import com.skiff.common.message.timer.DefaultMessageActuatorTask;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MessageTest {

    @Test
    public void t1() {
        MessageStorageService messageStorageService = new MemoryMessageStorageServiceImpl();
        new DefaultMessageActuatorTask(messageStorageService).schedule();
        new DefaultMessageActuatorRegister().register("com");


        for (int i = 1; i < 200; i++) {
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setId("message id = " + i);
            orderMessage.setMessage("order message");
            orderMessage.setOrderId(i + "");
            orderMessage.setDelay(i);
            messageStorageService.saveMessage(orderMessage);
        }


        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
