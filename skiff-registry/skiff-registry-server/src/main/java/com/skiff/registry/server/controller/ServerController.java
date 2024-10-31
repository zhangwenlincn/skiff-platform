package com.skiff.registry.server.controller;


import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.ListResult;
import com.skiff.common.core.result.Results;
import com.skiff.registry.server.bean.RegisteredServer;
import com.skiff.registry.server.grpc.CallbackService;
import com.skiff.registry.server.service.RegisteredService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/server")
public class ServerController {

    private final RegisteredService registeredService;

    private final CallbackService callbackService;


    @GetMapping(value = "/registered")
    public ListResult<RegisteredServer> getRegistered() {
        return Results.listResult(registeredService.getRegistered());
    }

    @GetMapping(value = "/callback")
    public BaseResult callback(@RequestParam(value = "serviceName") String serviceName) {
        RegisteredServer registered = registeredService.getRegistered(serviceName);
        callbackService.callback(registered);
        return Results.baseResult(true);
    }


}
