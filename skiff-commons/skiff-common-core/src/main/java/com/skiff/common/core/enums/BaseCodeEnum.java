package com.skiff.common.core.enums;

import lombok.Getter;

@Getter
public enum BaseCodeEnum implements BaseEnum {

    SUCCESS("10000", "操作成功"),
    ERROR("10001", "操作失败"),
    PARAMETER_FAIL("10002", "参数校验未通过"),
    ACTUATOR_FAIL("10003", "执行异常"),
    EXCEPTION_FAIL("10004", "操作异常"),
    ;

    private final String code;

    private final String message;

    BaseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
