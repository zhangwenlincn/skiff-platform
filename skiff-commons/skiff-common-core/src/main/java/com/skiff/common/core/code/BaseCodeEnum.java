package com.skiff.common.core.code;

import lombok.Getter;

@Getter
public enum BaseCodeEnum implements Code {

    SUCCESS("10000", "操作成功"),
    ERROR("10001", "操作失败"),
    AES_DECRYPT_FAIL("10010", "解密失败"),
    APP_NOT_FOUND("10011", "应用不存在"),
    PARAMETER_FAIL("10002", "参数校验未通过"),
    ACTUATOR_FAIL("10003", "执行异常"),
    EXCEPTION_FAIL("10004", "操作异常"),
    SERIALIZATION_FAIL("10005", "序列化错误"),
    DESERIALIZATION_FAIL("10006", "反序列化错误"),
    URI_PATH_NOT_FOUND("10007", "URI路径不存在"),
    REQUEST_METHOD_NOT_SUPPORTED("10008", "%s method not supported, only support [%s]"),
    MESSAGE_NOT_READABLE("10009", "required request body is missing"),
    ;

    private final String code;

    private final String message;

    BaseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
