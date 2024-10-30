package com.skiff.registrt.client.grpc;

import com.skiff.common.util.SpringUtil;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallbackServer {
    private static final Logger log = LoggerFactory.getLogger(CallbackServer.class);
    private final Integer port;

    public CallbackServer(Integer port) {
        this.port = port;
    }

    public void start() throws Exception {
        io.grpc.Server server = ServerBuilder.forPort(port)
                .addService(SpringUtil.getBean(GrpcCallbackServiceImpl.class))
                .build();
        server.start();
        server.awaitTermination();
        log.info("callback server start port:{}", port);
    }
}
