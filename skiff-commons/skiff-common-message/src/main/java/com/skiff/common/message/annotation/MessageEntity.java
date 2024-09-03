package com.skiff.common.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息实体注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MessageEntity {
    /**
     * message group name
     */
    String group() default "group";

    /**
     * message topic name
     */
    String topic() default "topic";
}
