package com.skiff.message.app.controller;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.message.app.message.OrderMessage;
import com.skiff.message.app.request.MessageRequest;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skiff.message.core.storage.MessageStorageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private MessageStorageService messageStorageService;

    /**
     * 模拟订单消息
     *
     * @param cnt 订单数量
     * @return BaseResult
     */
    @RequestMapping("/mock")
    public BaseResult mock(@RequestParam(value = "cnt") Integer cnt) {
        logger.info("模拟订单消息，订单数量：{}", cnt);
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

    @PostMapping(value = "/message")
    public BaseResult message(@Validated @RequestBody MessageRequest request) {
        return Results.baseResult(true);
    }

}

