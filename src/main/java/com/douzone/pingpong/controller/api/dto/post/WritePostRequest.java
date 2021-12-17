package com.douzone.pingpong.controller.api.dto.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class WritePostRequest {
    private String title;
    private String contents;
    private MultipartFile imageFile;
    private List<MultipartFile> attachFiles;
}
