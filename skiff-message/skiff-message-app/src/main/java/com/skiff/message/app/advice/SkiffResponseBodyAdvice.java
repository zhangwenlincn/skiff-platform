//package com.skiff.message.app.advice;
//
//import com.skiff.common.core.result.BaseResult;
//import com.skiff.common.core.result.Result;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.lang.Nullable;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//@ControllerAdvice
//public class SkiffResponseBodyAdvice implements ResponseBodyAdvice<Object> {
//
//    @Override
//    public boolean supports(@Nullable MethodParameter returnType
//            , @Nullable Class converterType) {
//        return true;
//    }
//
//
//    @Override
//    public Object beforeBodyWrite(Object body
//            , @Nullable MethodParameter returnType
//            , @Nullable MediaType selectedContentType
//            , @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType
//            , @Nullable ServerHttpRequest request
//            , @Nullable ServerHttpResponse response) {
//        if (body instanceof Result<?>) {
//            if (((Result<?>) body).isSuccess()) {
//                return body;
//            } else {
//                return new BaseResult(false, ((Result<?>) body).getCode(), ((Result<?>) body).getMessage());
//            }
//        }
//        return body;
//    }
//}
