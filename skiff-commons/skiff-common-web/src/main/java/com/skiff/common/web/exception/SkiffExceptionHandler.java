package com.skiff.common.web.exception;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;


@RestControllerAdvice
public class SkiffExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SkiffExceptionHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    public BaseResult handleNoResourceFoundException(NoResourceFoundException e) {
        return Results.baseResult(BaseCodeEnum.URI_PATH_NOT_FOUND.getCode(), String.format("[%s] %s"
                , e.getResourcePath(), BaseCodeEnum.URI_PATH_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Results.baseResult(BaseCodeEnum.REQUEST_METHOD_NOT_SUPPORTED.getCode()
                , String.format(BaseCodeEnum.REQUEST_METHOD_NOT_SUPPORTED.getMessage()
                        , e.getMethod(), String.join(","
                                , Objects.requireNonNull(e.getSupportedMethods()))));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Results.baseResult(BaseCodeEnum.MESSAGE_NOT_READABLE);
    }

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
