package com.douzone.pingpong.domain.member;

import com.douzone.pingpong.domain.post.Comment;
import com.douzone.pingpong.domain.post.PostMember;
import com.douzone.pingpong.web.JoinForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostMember> postMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    private String email;
    private String password;
    private String name;
    private String phone;
    private String company;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    private LocalDateTime date;
    private String avatar;

    @Builder
    public Member (JoinForm form) {
        this.email = form.getEmail();
        this.password = form.getPassword();
        this.name = form.getName();
        this.phone = form.getPhone();
        this.company = form.getCompany();
        this.date = LocalDateTime.now();
    }
}
