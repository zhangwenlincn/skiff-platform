package com.skiff.common.exception;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleException(MethodArgumentNotValidException e) {
        log.error("参数校验未通过:{}", e.getAllErrors(), e);
        return new BaseResult(false, BaseCodeEnum.PARAMETER_FAIL.getCode(), e.getAllErrors().getFirst().getDefaultMessage());
    }

    @ExceptionHandler(SkiffException.class)
    public BaseResult handleException(SkiffException e) {
        log.error("自定义异常 SkiffException:{}", e.getMessage(), e);
        return new BaseResult(false, e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult handleRuntimeException(RuntimeException e) {
        log.error("运行时异常 RuntimeException:{}", e.getMessage(), e);
        return new BaseResult(false, BaseCodeEnum.EXCEPTION_FAIL.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        log.error("系统异常 Exception:{}", e.getMessage(), e);
        return new BaseResult(false, BaseCodeEnum.EXCEPTION_FAIL.getCode(), e.getMessage());
    }
}
