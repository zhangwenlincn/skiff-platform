package com.skiff.common.message.register;

/**
 * 消息执行器注册
 */
public interface MessageActuatorRegister {

    void register(String... packages);
}