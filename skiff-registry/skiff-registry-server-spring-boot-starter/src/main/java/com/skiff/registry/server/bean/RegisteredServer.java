package com.skiff.registry.server.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 注册服务信息
 */
@Data
public class RegisteredServer implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    public RegisteredServer() {

    }

    public RegisteredServer(String serviceName, Set<String> address) {
        this.serviceName = serviceName;
        this.address = address;
    }

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务地址 ip:port
     */
    private Set<String> address;
}
