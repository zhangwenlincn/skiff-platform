package com.skiff.registrt.server.grpc;

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

    public void start() throws Exception {
        io.grpc.Server server = ServerBuilder.forPort(port)
                .addService(SpringUtil.getBean(GrpcRegistryServiceImpl.class))
                .build();
        server.start();
        server.awaitTermination();
        log.info("registry server start port:{}", port);
    }

}
