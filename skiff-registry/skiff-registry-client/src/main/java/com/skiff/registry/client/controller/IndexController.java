package com.skiff.registry.client.controller;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping
    public BaseResult index() {
        return Results.baseResult(true);
    }
}
