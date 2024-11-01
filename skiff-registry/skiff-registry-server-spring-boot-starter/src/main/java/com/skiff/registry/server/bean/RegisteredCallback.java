package com.skiff.registry.server.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RegisteredCallback implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    private String serviceName;

    private String ip;

    private Integer port;

    private String bean;

    private String params;
}
