package com.douzone.pingpong.domain.post;

import com.douzone.pingpong.controller.api.dto.post.UpdatePostRequest;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDto {
    private String title;
    private String contents;
    private String thumbnail;

    private Long partId;

    public PostDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.thumbnail = post.getThumbnail();
        this.partId = post.getPart().getId();
    }
}
