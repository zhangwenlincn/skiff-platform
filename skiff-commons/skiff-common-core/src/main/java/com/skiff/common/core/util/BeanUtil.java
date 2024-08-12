package com.skiff.common.core.util;


import org.modelmapper.ModelMapper;

import java.util.List;

public class BeanUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ModelMapper getInstance() {
        return modelMapper;
    }

    public static <T> T copy(Object source, Class<T> targetClass) {
        return BeanUtil.getInstance().map(source, targetClass);
    }

    public static <T> List<T> copyList(List<Object> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> copy(source, targetClass)).toList();
    }
}