package com.skiff.common.exception;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.result.BaseResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleException(MethodArgumentNotValidException e) {
        return new BaseResult(false, BaseCodeEnum.PARAMETER_FAIL.getCode(), e.getAllErrors().getFirst().getDefaultMessage());
    }
}
