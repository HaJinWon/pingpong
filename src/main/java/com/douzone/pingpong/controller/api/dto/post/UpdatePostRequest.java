package com.douzone.pingpong.controller.api.dto.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdatePostRequest {
    private String title;
    private String contents;
    private String thumbnail;
}
