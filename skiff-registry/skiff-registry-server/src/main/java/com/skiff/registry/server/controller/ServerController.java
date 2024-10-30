package com.skiff.registry.server.controller;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.protobuf.callback.RpcCallback;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.content.RegistryContent;
import com.skiff.registry.server.grpc.CallbackServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/server")
public class ServerController {


    private final CallbackServiceImpl callbackServiceImpl;

    private final RegistryContent registryContent;

    public ServerController(CallbackServiceImpl callbackServiceImpl, RegistryContent registryContent) {
        this.callbackServiceImpl = callbackServiceImpl;
        this.registryContent = registryContent;
    }

    @GetMapping(value = "/callback")
    public BaseResult register() {
        List<RpcRegistry.RegistryRequest> registryServiceList = registryContent.getRegistryServiceList("registry-client");
        RpcCallback.CallbackRequest.Builder request = RpcCallback.CallbackRequest.newBuilder();
        request.setBean("test");
        request.setParams("{test}");
        RpcResult.Result callback = callbackServiceImpl.callback(request.build(), registryServiceList.getFirst());
        return Results.baseResult(callback.getCode(), callback.getMessage());
    }
}
