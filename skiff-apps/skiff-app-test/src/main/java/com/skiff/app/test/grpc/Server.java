package com.skiff.app.test.grpc;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server = ServerBuilder.forPort(50051)
                .addService(new MessageGrpcServiceImpl())
                .build();
        server.start();
        System.out.println("Server started.");
        server.awaitTermination();
    }
}
