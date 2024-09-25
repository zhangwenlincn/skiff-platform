package com.skiff.app.test.smartdoc;

import com.skiff.common.core.result.ObjectResult;
import com.skiff.common.core.result.Results;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试是否能生成smartdoc文档
 *
 * @author 张文林
 * @since 2024年9月25日
 */
@RestController
@RequestMapping("/smartdoc")
public class SmartDocController {


    /**
     * 测试smartdoc文档
     *
     * @param request 请求参数
     * @return 响应结果
     */
    @PostMapping("/p1")
    public ObjectResult<SmartDocResponseBody> p1(@RequestBody SmartDocRequest request) {
        return Results.objectResult(new SmartDocResponseBody());
    }
    /**
     * 测试smartdoc文档2
     *
     * @param request 请求参数
     * @return 响应结果
     */
    @PostMapping("/p2")
    public ObjectResult<SmartDocResponseBody> p2(@RequestBody SmartDocRequest request) {
        return Results.objectResult(new SmartDocResponseBody());
    }
}
