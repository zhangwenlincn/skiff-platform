package com.skiff.common.core.result;

public interface Result<T> {

    String getCode();

    boolean isSuccess();

    String getMessage();

    T getData();
}
