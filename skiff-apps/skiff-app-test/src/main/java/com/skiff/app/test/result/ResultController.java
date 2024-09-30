package com.skiff.app.test.result;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.ObjectResult;
import com.skiff.common.core.result.Results;
import com.skiff.common.core.util.BeanUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/result")
@RestController
public class ResultController {


    @RequestMapping("/getBaseResult")
    public BaseResult getResult() {
        return Results.baseResult(true);
    }

    @RequestMapping("/getObjectResult1")
    public ObjectResult<String> getObjectResult() {
        return Results.objectResult(true);
    }

    @RequestMapping("/getObjectResult2")
    public ObjectResult<String> getObjectResult2() {
        return Results.objectResult("true");
    }


    @PostMapping("/getResultRequest")
    public ObjectResult<ResultResponse> getResultRequest(@RequestBody ResultRequest resultRequest) {
        return Results.objectResult(BeanUtil.copy(resultRequest, ResultResponse.class));
    }
    @PostMapping("/getResultRequest2")
    public ObjectResult<ResultResponse> getResultRequest2(@RequestBody ResultRequest resultRequest) {
        return Results.objectResult(BeanUtil.copy(resultRequest, ResultResponse.class));
    }

    @PostMapping("/getResultFrom")
    public ObjectResult<ResultResponse> getResultFrom(ResultRequest resultRequest) {
        return Results.objectResult(BeanUtil.copy(resultRequest, ResultResponse.class));
    }


}


