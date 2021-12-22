package com.douzone.pingpong.domain.post;

import com.douzone.pingpong.domain.comment.Comment;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    List<PostMember> postMembers = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    List<UploadFile> uploadFiles = new ArrayList<>();

    private String contents;

    private LocalDateTime date;

    private String thumbnail;

    private String title;

    public Post() {
        this.date = LocalDateTime.now();
    }

    // == 생성 메소드 == //
    public static Post writePost(String title, String contents, String thumbnail, Member member, Part part) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        post.setThumbnail(thumbnail);
        post.setMember(member);
        post.setPart(part);

        return post;
    }

    // == 연관관계 메서드 == //
    public void setMember (Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void setPart (Part part) {
        this.part = part;
        part.getPosts().add(this);
    }

    // == 비지니스 로직 ==//
    /**
     * 게시판 수정 (UPDATE)
     */
    public void editPost(String title, String contents, String thumbnail) {
        this.title = title;
        this.contents = contents;
        this.thumbnail = thumbnail;
    }
}