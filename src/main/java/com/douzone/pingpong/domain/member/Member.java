package com.douzone.pingpong.domain.member;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.RoomMember;
import com.douzone.pingpong.domain.post.Comment;
import com.douzone.pingpong.domain.post.PostMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
public class Member implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostMember> postMembers = new ArrayList<>();

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
