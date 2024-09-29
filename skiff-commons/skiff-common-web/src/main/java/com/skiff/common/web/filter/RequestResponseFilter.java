package com.skiff.common.web.filter;

import com.skiff.common.core.util.JsonUtil;
import com.skiff.common.web.wrapper.SkiffHttpServletRequestWrapper;
import com.skiff.common.web.wrapper.SkiffHttpServletResponseWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestResponseFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(RequestResponseFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("Request method: {}, uri: {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        log.info("Request remote address: {}", httpServletRequest.getRemoteAddr());
        log.info("Request parameters: {}", JsonUtil.toJson(httpServletRequest.getParameterMap()));
        SkiffHttpServletRequestWrapper requestWrapper = new SkiffHttpServletRequestWrapper(httpServletRequest);
        String requestBody = readRequestBody(requestWrapper);
        log.info("Request body: {}", requestBody);
        //模拟填充参数
        //requestWrapper.addParameter("cnt", "222");
        //模拟重新设置body
        //String body = "{\"name\":\"中文\",\"age\":18}";
        requestWrapper.setBody(requestBody);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        SkiffHttpServletResponseWrapper skiffHttpServletResponseWrapper = new SkiffHttpServletResponseWrapper(httpServletResponse);
        // 继续过滤器链
        chain.doFilter(requestWrapper, skiffHttpServletResponseWrapper);
        byte[] responseBody = skiffHttpServletResponseWrapper.getCopyOfResponseBody();
        Map<String, Object> result = JsonUtil.toMap(new String(responseBody, StandardCharsets.UTF_8));
        log.info("Response status: {}, skiff code: {}, success: {}, message: {}", httpServletResponse.getStatus(), result.get("code"), result.get("success"), result.get("message"));
        //拿到responseBody，然后再次封装成Result对象，并设置到responseWrapper中
        response.getOutputStream().write(responseBody);
    }

    private String readRequestBody(SkiffHttpServletRequestWrapper request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}
