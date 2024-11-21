package com.skiff.common.core.code;

import com.skiff.common.core.util.ThreadLocalUtil;
import lombok.Getter;

import java.util.Optional;

@Getter
public enum BaseCodeEnum implements Code {

    SUCCESS("10000", "操作成功", "success"),
    ERROR("10001", "操作失败", "error"),
    EXCEPTION_FAIL("10002", "操作异常", "exception"),
    DESERIALIZATION_FAIL("10003", "反序列化错误", "deserialization fail"),
    PARAMETER_FAIL("10004", "参数校验未通过", "parameter fail"),
    SERIALIZATION_FAIL("10005", "序列化错误", "serialization fail"),
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

    BaseCodeEnum(String code, String zh, String en) {
        this.code = code;
        this.zh = zh;
        this.en = en;
    }
}
