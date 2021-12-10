package com.douzone.pingpong.domain.chat;

import com.douzone.pingpong.domain.team.Team;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
//    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "room")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    private String title;


    public static Room createRoom(RoomMember roomMember, Team team, String title ) {
        Room chatRoom = new Room();
        chatRoom.title = title;
        chatRoom.team = team;
        chatRoom.roomMembers.add(roomMember);
        return chatRoom;
    }

    // == 연관관계 메서드 == //

}



