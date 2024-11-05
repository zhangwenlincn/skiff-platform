package com.skiff.registry.server.jdbc;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RegistryServerInfo implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String ip;

    private Integer port;

    private Integer status = 1;

    private String description;

    private LocalDateTime registerTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
