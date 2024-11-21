package com.skiff.message.enums;

import com.skiff.common.core.code.Code;
import com.skiff.common.core.code.LanguageEnum;
import com.skiff.common.core.util.ThreadLocalUtil;
import lombok.Getter;

import java.util.Optional;

@Getter
public enum MessageCodeEnum implements Code {

    MESSAGE_FAIL("20001", "消息服务异常", "message fail"),
    ;
    /**
     * 状态码
     */
    private final String code;

    private final String zh;

    private final String en;

    @Override
    public String getMessage() {
        LanguageEnum languageEnum = Optional.ofNullable(ThreadLocalUtil.getLanguage()).orElse(LanguageEnum.zh_CN);
        return switch (languageEnum) {
            case zh_CN -> zh;
            case en_US -> en;
        };
    }

    MessageCodeEnum(String code, String zh, String en) {
        this.code = code;
        this.zh = zh;
        this.en = en;
    }
}
