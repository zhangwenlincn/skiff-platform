package com.skiff.app.test.smartdoc;

import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * SmartDoc请求参数
 *
 * @author <NAME>
 * @since 2024年9月25日
 */
@Data
public class SmartDocRequest implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文档类型
     */
    private String docType;

    /**
     * 文档内容
     */
    private List<String> list;
}
