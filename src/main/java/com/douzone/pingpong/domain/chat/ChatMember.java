package com.douzone.pingpong.domain.chat;

import com.douzone.pingpong.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_member")
@Getter
public class ChatMember {

    @Id @GeneratedValue
    @Column(name = "chat_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "chatMember")
    private List<Chat> chats = new ArrayList<>();
}
