package com.skiff.app.test.grpc;

import com.skiff.protobuf.entity.Message;
import com.skiff.protobuf.result.RpcResult;
import com.skiff.protobuf.rpc.MessageServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);

        Message.MessageRequest request = Message.MessageRequest.newBuilder().setContent("Hello, world!").build();

        RpcResult.Result result = stub.sendMessage(request);

        System.out.println(result.getCode());
        System.out.println(result.getMessage());

        channel.shutdown();
    }
}
