package com.douzone.pingpong.domain.post;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;


    private String contents;
    private LocalDateTime date;

    private String file;
    private String image;
    private String title;

//     Post_momber 테이블 삭제로 인한 주석처리 : 1204 JIN
//    @OneToMany(mappedBy = "post")
//    List<PostMember> postMembers = new ArrayList<>();
}
