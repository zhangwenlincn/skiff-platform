//package com.skiff.message.app.advice;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.lang.Nullable;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//
//@ControllerAdvice
//public class SkiffRequestBodyAdvice extends RequestBodyAdviceAdapter {
//    @Override
//    public boolean supports(@Nullable MethodParameter methodParameter, @Nullable Type targetType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public HttpInputMessage beforeBodyRead(@Nullable HttpInputMessage inputMessage, @Nullable MethodParameter parameter, @Nullable Type targetType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
//        assert parameter != null;
//        assert inputMessage != null;
//        assert targetType != null;
//        assert converterType != null;
//        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
//    }
//}
