package com.douzone.pingpong.controller.api.dto.file;

import com.douzone.pingpong.domain.file.UploadFile;
import lombok.Data;

@Data
public class UploadFileResponse {
    private String origFileName;
    private String storeFileName;
    private String filePath;

    public UploadFileResponse(UploadFile uploadFile) {
        this.origFileName = uploadFile.getOrigFilename();
        this.storeFileName = uploadFile.getFilename();
        this.filePath = uploadFile.getFilePath();
    }
}
