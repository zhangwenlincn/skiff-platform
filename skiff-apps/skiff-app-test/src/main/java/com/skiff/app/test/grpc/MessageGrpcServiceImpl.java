package com.skiff.app.test.grpc;

import com.google.protobuf.ByteString;
import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.protobuf.entity.Message;
import com.skiff.protobuf.result.RpcResult;
import com.skiff.protobuf.rpc.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.nio.charset.StandardCharsets;


public class MessageGrpcServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {
    @Override
    public void sendMessage(Message.MessageRequest request, StreamObserver<RpcResult.Result> streamObserver) {
        RpcResult.Result result = RpcResult.Result.newBuilder()
                .setSuccess(true)
                .setCode(BaseCodeEnum.SUCCESS.getCode())
                .setMessage(BaseCodeEnum.SUCCESS.getMessage())
                .setData(ByteString.copyFrom(request.getContent().getBytes(StandardCharsets.UTF_8)))
                .build();

        System.out.println("Received content: " + request.getContent());

        streamObserver.onNext(result);
        streamObserver.onCompleted();
    }
}
