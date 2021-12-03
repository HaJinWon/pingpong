package com.douzone.pingpong.domain.member;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.RoomMember;
import com.douzone.pingpong.domain.post.Comment;
import com.douzone.pingpong.domain.post.PostMember;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostMember> postMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Chat> chats = new ArrayList<>();

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
    public Member (String email, String password, String name, String phone, String company, LocalDateTime date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.company = company;
        this.date = date;
    }

    public void updateMember(String name, MemberStatus status, String avatar) {
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }

}
