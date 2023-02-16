package com.orange.mo.common.file;

import lombok.Data;

@Data
public class FileStorageData {
    private String originalName;
    private String fileName;
    private String fileUrl;
    private String base64;
}
