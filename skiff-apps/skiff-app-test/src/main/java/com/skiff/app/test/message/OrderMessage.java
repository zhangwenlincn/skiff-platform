package com.skiff.app.test.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skiff.message.core.annotation.MessageEntity;
import skiff.message.core.base.BaseMessage;


@Data
@EqualsAndHashCode(callSuper = false)
@MessageEntity(group = "order", topic = "order")
public class OrderMessage extends BaseMessage {
    private String orderId;

}
