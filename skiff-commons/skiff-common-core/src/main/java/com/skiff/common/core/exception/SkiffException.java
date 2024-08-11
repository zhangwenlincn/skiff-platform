package com.skiff.common.core.exception;

import com.skiff.common.core.enums.BaseCodeEnum;
import com.skiff.common.core.enums.BaseEnum;
import lombok.Getter;

@Getter
public class SkiffException extends RuntimeException {


    private final String code;

    private final String message;

    public SkiffException(BaseEnum baseEnum) {
        super(baseEnum.getMessage());
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public SkiffException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SkiffException(String message) {
        super(message);
        this.code = BaseCodeEnum.EXCEPTION_FAIL.getCode();
        this.message = message;
    }

}
