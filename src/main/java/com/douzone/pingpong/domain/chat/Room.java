package com.douzone.pingpong.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

//    @OneToMany(mappedBy = "room")
//    private List<RoomMember> roomMembers = new ArrayList<>();
//
//    @OneToMany(mappedBy = "room")
//    private List<Chat> chats = new ArrayList<>();

    private String title;

    @Builder
    public Room(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Room create(String title) {
        Room chatRoom = new Room();
        chatRoom.title = title;
        return chatRoom;
    }
}



