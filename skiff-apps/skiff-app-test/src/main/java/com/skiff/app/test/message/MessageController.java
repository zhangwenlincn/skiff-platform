package com.skiff.app.test.message;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.common.message.storage.MessageStorageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Resource
    private MessageStorageService messageStorageService;


    @RequestMapping("/m1")
    public BaseResult m1(@RequestParam(value = "cnt") Integer cnt) {
        for (int i = 1; i < (cnt + 1); i++) {
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setId("message id = " + i);
            orderMessage.setMessage("order message");
            orderMessage.setOrderId(i + "");
            orderMessage.setDelay(i);
            messageStorageService.saveMessage(orderMessage);
        }
        return Results.baseResult(true);
    }
}
