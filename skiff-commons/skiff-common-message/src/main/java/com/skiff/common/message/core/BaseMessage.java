package com.skiff.common.message.core;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Message基类
 */
@Data
public abstract class BaseMessage implements Message, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private static Long NOW_TIME_MILLIS;

    BaseMessage() {
        NOW_TIME_MILLIS = System.currentTimeMillis();
    }

    /**
     * 消息ID
     */
    private String id;

    /*
     *消息内容
     */
    private String message;

    /**
     * 消息延迟时间
     */
    private Integer delay;

    /**
     * 消息消费时间
     */
    @Override
    public Long getExecuteTimestamp() {
        return NOW_TIME_MILLIS + delay * 1000L;
    }
}
