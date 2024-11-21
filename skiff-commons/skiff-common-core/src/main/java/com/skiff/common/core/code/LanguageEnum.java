package com.skiff.common.core.code;

import lombok.Getter;

@Getter
public enum LanguageEnum {
    zh_CN("中文简体"),
    en_US("English");

    private final String desc;

    LanguageEnum(String desc) {
        this.desc = desc;
    }
}
