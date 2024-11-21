package com.skiff.common.interceptor;

import com.skiff.common.core.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class ThreadLocalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在请求处理之前执行的逻辑
        ThreadLocalUtil.setLanguage(Optional.ofNullable(request.getHeader("Accept-Language")).orElse("zh_CN"));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后，但在视图渲染之前执行的逻辑
        // 如果控制器方法没有返回ModelAndView，则modelAndView参数为null
        response.setHeader("Accept-Language", ThreadLocalUtil.getLanguage().name());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求完成之后执行的逻辑，无论请求是否成功
        ThreadLocalUtil.remove();
    }
}
