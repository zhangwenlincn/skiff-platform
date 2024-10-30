package com.skiff.registry.server.grpc;

import com.skiff.common.util.SpringUtil;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistryServer {

    private static final Logger log = LoggerFactory.getLogger(RegistryServer.class);
    private final Integer port;

    public RegistryServer(Integer port) {
        this.port = port;
    }

    public void start() {

        new Thread(() -> {
            io.grpc.Server server = ServerBuilder.forPort(port)
                    .addService(SpringUtil.getBean(GrpcRegistryServiceImpl.class))
                    .build();
            try {
                server.start();
                log.info("registry server start port:{}", port);
                server.awaitTermination();
                log.info("registry server start port:{}", port);
            } catch (Exception e) {
                log.error("registry server start error", e);
                throw new RuntimeException(e);
            }

        }).start();

    }

}
