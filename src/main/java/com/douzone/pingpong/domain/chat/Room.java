package com.douzone.pingpong.domain.chat;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Room {
    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @OneToMany(mappedBy = "room")
    private List<ChatMember> chatMembers = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    private String title;

    public static Room create(String title) {
        Room room = new Room();
        room.title = title;
        return room;
    }
}
