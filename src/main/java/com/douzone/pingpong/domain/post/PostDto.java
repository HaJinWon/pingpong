package com.douzone.pingpong.domain.post;

import com.douzone.pingpong.controller.api.dto.post.UpdatePostRequest;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDto {
    private String title;
    private String contents;
    private MultipartFile imageFile;
    private List<MultipartFile> attachFiles;

    public PostDto(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.contents = request.getContents();
        this.imageFile = request.getImageFile();
        this.attachFiles = request.getAttachFiles();
    }
}
