package com.skiff.common.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;

public class BeanUtil {

    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

    public static <T> T copy(Object source, Class<T> targetClass) {
        try {
            Constructor<T> declaredConstructor = targetClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            T result = declaredConstructor.newInstance();
            BeanUtils.copyProperties(source, result);
            return result;
        } catch (Exception e) {
            log.error("类属性复制异常", e);
            throw new RuntimeException("类属性复制异常");
        }
    }

    public static <T> List<T> copyList(List<Object> sourceList, Class<T> targetClass) {
        try {
            return sourceList.stream().map(source -> copy(source, targetClass)).toList();
        } catch (Exception e) {
            log.error("类集合属性复制异常", e);
            throw new RuntimeException("类集合属性复制异常");
        }
    }
}