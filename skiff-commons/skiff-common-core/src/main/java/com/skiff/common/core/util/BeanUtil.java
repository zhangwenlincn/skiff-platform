package com.skiff.common.core.util;


import com.skiff.common.core.bean.BeanEnum;
import org.modelmapper.ModelMapper;

import java.util.List;

public class BeanUtil {


    public static ModelMapper getInstance() {
        return BeanEnum.INSTANCE.getInstance().getModelMapper();
    }


    public static <T> T copy(Object source, Class<T> targetClass) {
        return BeanUtil.getInstance().map(source, targetClass);
    }

    public static <T> List<T> copyList(List<Object> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> copy(source, targetClass)).toList();
    }
}