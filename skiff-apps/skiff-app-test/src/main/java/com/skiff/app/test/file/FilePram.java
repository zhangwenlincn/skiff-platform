package com.skiff.app.test.file;

import lombok.Data;

@Data
public class FilePram {
    private String fileName;
    private String fileContent;
    private String fileExtension;

    private String filePath;
}
