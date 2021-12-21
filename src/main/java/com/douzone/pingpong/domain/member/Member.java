package com.douzone.pingpong.domain.member;

import com.douzone.pingpong.controller.api.dto.member.UpdateMemberDto;
import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private static final long serialVersionUID = 11531664L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private String company;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "upload_file_id")
//    private UploadFile uploadFile;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDateTime date;

    private String avatar;

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

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "upload_file_id")
//    private UploadFile uploadFile;


    @Builder
    public Member (String email, String password, String name, String phone, String company, LocalDateTime date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.company = company;
        this.date = date;
        this.avatar = "avatar.jpg";
    }

    public void updateMember(UpdateMemberDto memberDto) {
        this.name = memberDto.getName();
        this.company= memberDto.getCompany();
        this.phone = memberDto.getPhone();
        this.status = memberDto.getStatus();
        this.avatar = memberDto.getAvatar();
//        this.setImage(memberDto.getImage());
    }

    // == 연관관계 메서드 == //
//    public void setImage(UploadFile image) {
//        this.uploadFile=image;
//        image.setMember(this);
//    }



}
