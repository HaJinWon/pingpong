package com.douzone.pingpong.domain.chat;

import com.douzone.pingpong.domain.team.Team;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
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
//    private Long id;
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "room")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    private String title;

//    @Builder
//    public Room(Long id, String title) {
//        this.id = id;
//        this.title = title;
//    }

    public static Room create(String title) {
        Room chatRoom = new Room();
        chatRoom.id = UUID.randomUUID().toString();
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



