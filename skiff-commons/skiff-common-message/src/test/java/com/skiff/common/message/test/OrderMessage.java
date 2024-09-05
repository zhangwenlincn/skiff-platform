package com.skiff.common.message.test;

import com.skiff.common.message.annotation.MessageEntity;
import com.skiff.common.message.core.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@MessageEntity(group = "order", topic = "order")
public class OrderMessage extends BaseMessage {
    private String orderId;

}
