package com.douzone.pingpong.domain.file;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UploadFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;
//
//    public UploadFile toEntity() {
//        UploadFile build = UploadFile.builder()
//                .origFilename(origFilename)
//                .filename(filename)
//                .filePath(filePath)
//                .build();
//        return build;
//    }

    @Builder
    public UploadFileDto(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
