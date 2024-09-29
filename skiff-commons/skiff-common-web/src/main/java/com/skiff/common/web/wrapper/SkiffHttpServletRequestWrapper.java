package com.skiff.common.web.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SkiffHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> requestParameters = new java.util.HashMap<>();


    @Setter
    @Getter
    private String body;


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public SkiffHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestParameters.putAll(request.getParameterMap());
        body = initBody(request);
    }


    /**
     * Add a parameter to the request.
     *
     * @param name  the name of the parameter.
     * @param value the value of the parameter.
     */
    public void addParameter(String name, String value) {
        requestParameters.put(name, new String[]{value});
    }

    @Override
    public String getParameter(String name) {
        String[] values = requestParameters.get(name);
        return (values != null && values.length > 0) ? values[0] : super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return requestParameters;
    }

    @Override
    public String[] getParameterValues(String name) {
        return requestParameters.get(name);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new StringServletInputStream(body);
    }


    public String initBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }


    private static class StringServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream byteArrayInputStream;

        public StringServletInputStream(String s) {
            this.byteArrayInputStream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public int read() {
            return byteArrayInputStream.read();
        }

        @Override
        public boolean isFinished() {
            // Servlet 4.0 引入了此方法，但在之前的版本中需要自行实现
            // 返回输入流是否已到达末尾
            return byteArrayInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            // Servlet 4.0 引入了此方法，但在之前的版本中可能不需要实现
            // 通常可以简单地返回 true，因为 ByteArrayInputStream 总是“就绪”的
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }


        // 可选：添加其他方法，如 read(byte[] b, int off, int len) 以提高效率
        //@Override
        //public int read(byte[] bytes, int off, int len) throws IOException {
        //    return byteArrayInputStream.read(bytes, off, len);
        //}

        // 注意：如果你需要支持 Servlet 3.1 之前的版本，你可以忽略 isFinished() 和 isReady() 方法
        // 因为它们是在 Servlet 4.0 中引入的。
    }
}
