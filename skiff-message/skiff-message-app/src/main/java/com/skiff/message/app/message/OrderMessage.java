package com.skiff.message.app.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skiff.message.core.annotation.MessageEntity;
import skiff.message.core.base.BaseMessage;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@MessageEntity(group = "order", topic = "order")
public class OrderMessage extends BaseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String orderId;
}
