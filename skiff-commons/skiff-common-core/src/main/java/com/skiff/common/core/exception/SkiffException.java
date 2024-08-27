package com.skiff.common.core.exception;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.code.Code;
import lombok.Getter;

@Getter
public class SkiffException extends RuntimeException {


    private final String code;

    private final String message;

    private Throwable e;

    public SkiffException(Code baseEnum) {
        super(baseEnum.getMessage());
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public SkiffException(Code baseEnum, Throwable e) {
        super(baseEnum.getMessage(), e);
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.e = e;
    }

    public SkiffException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SkiffException(String code, String message, Throwable e) {
        super(message, e);
        this.code = code;
        this.message = message;
        this.e = e;
    }

    public SkiffException(String message) {
        super(message);
        this.code = BaseCodeEnum.EXCEPTION_FAIL.getCode();
        this.message = message;
    }

    public SkiffException(String message, Throwable e) {
        super(message, e);
        this.code = BaseCodeEnum.EXCEPTION_FAIL.getCode();
        this.message = message;
        this.e = e;
    }

}
