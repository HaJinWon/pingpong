package com.douzone.pingpong.domain.post;

import com.douzone.pingpong.domain.comment.Comment;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.PostMember;
import com.douzone.pingpong.domain.part.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    @OneToMany(mappedBy = "post")
    List<PostMember> postMembers = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    List<UploadFile> uploadFiles = new ArrayList<>();

    private String contents;

    private LocalDateTime date;

    private String file;

    private String image;

    private String title;

    public Post() {
        this.date = LocalDateTime.now();
    }

    // == 생성 메소드 == //
    public static Post writePost(String title, String contents, UploadFile imageFile, List<UploadFile> attachFiles) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        post.setImage(imageFile);
        post.setAttachFiles(attachFiles);

        return post;
    }

    // == 연관관계 메서드 == //
    public void setImage(UploadFile uploadFile) {
        this.uploadFiles.add(uploadFile);
        uploadFile.setPost(this);
    }

    public void setAttachFiles(List<UploadFile> uploadFiles) {
        for (UploadFile uploadFile : uploadFiles) {
            this.uploadFiles.add(uploadFile);
            uploadFile.setPost(this);
        }
    }
}
