package com.skiff.common.core.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FileInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 文件唯一标识码
     */
    private String fileCode;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String fileUrl;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long fileSize;

}
