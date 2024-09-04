package com.skiff.common.message.core;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Message基类
 */
@Data
public class BaseMessage implements Message, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 当前时间戳
     */
    private static Long NOW_TIME_MILLIS;


    /**
     * 默认重试次数
     */
    private static final Integer DEFAULT_RETRY_TIMES = 3;

    /**
     * 构造方法
     */
    public BaseMessage() {
        NOW_TIME_MILLIS = System.currentTimeMillis();
    }

    /**
     * 消息ID
     */
    private String id;

    /*
     * 消息内容
     */
    private String message;

    /**
     * 消息延迟时间(默认1秒)
     */
    private Integer delay  = 1;

    /**
     * 消息重试次数
     */
    private Integer retryTimes;

    /**
     * 消息消费时间
     */
    @Override
    public Long getExecuteTimestamp() {
        return NOW_TIME_MILLIS + delay * 1000L;
    }

    @Override
    public Integer getRetryTimes() {
        if (retryTimes == null || retryTimes <= 0) {
            return DEFAULT_RETRY_TIMES;
        }
        return retryTimes;
    }
}
