package com.douzone.pingpong.domain.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @OneToMany(mappedBy = "room")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

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

    public static Room create(RoomMember roomMember,String title ) {
        Room chatRoom = new Room();
        chatRoom.title = title;
        chatRoom.roomMembers.add(roomMember);
        return chatRoom;
    }

    // == 연관관계 메서드 == //

}



