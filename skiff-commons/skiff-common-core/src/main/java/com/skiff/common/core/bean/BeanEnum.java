package com.skiff.common.core.bean;

import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
public enum BeanEnum {

    INSTANCE;

    private final ModelMapper modelMapper;

    BeanEnum() {
        this.modelMapper = new ModelMapper();
    }

    public BeanEnum getInstance() {
        return INSTANCE;
    }
}
