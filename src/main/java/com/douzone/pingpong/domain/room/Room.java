package com.douzone.pingpong.domain.room;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.member.RoomMember;
import com.douzone.pingpong.domain.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Room implements Serializable {
    private static final long serialVersionUID = 1651894651651487L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
//    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    private String title;
    private String notice;
    private LocalDateTime date;


    public static Room createRoom(RoomMember roomMember, Team team, String title ) {
        Room chatRoom = new Room();
        chatRoom.title = title;
        chatRoom.team = team;
        chatRoom.date = LocalDateTime.now();
        chatRoom.addRoomMember(roomMember);
        return chatRoom;
    }

    // == 연관관계 메서드 == //
    public void addRoomMember(RoomMember roomMember) {
        roomMembers.add(roomMember);
        roomMember.setRoom(this);
    }

    // == 비지니스 로직 == //
    /**
     * 공지사항(Notice) 등록
     */
    public void updateNotice(String notice) {
        this.notice = notice;
    }

}



