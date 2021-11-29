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

    @OneToMany(mappedBy = "post")
    List<PostMember> postMembers = new ArrayList<>();

    private String title;
    private String contents;
    private LocalDateTime date;

    private String file;
    private String image;
}
