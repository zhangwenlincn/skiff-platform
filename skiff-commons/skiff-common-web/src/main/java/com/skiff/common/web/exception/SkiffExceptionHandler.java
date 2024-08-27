package com.skiff.common.web.exception;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class SkiffExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SkiffExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        log.info("handleException: ", e);
        return Results.baseResult(BaseCodeEnum.EXCEPTION_FAIL);
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult handleRuntimeException(RuntimeException e) {
        log.info("handleRuntimeException: ", e);
        return Results.baseResult(BaseCodeEnum.EXCEPTION_FAIL.getCode(), e.getMessage());
    }

    @ExceptionHandler(SkiffException.class)
    public BaseResult handleSkiffException(SkiffException e) {
        log.info("handleSkiffException: ", e);
        return Results.baseResult(e.getCode(), e.getMessage());
    }

}
