package com.douzone.pingpong.domain.chat;

import com.douzone.pingpong.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_member")
@Getter
@NoArgsConstructor
@DynamicInsert
public class RoomMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    /*
    @Builder
    public RoomMember(Member member) {
        this.member = member;
    }

    //== 생성 메서드 == //
    public static RoomMember createRoomMember(Member member) {
        return  RoomMember.builder()
                .member(member)
                .build();
    }
    */

    @Builder
    public RoomMember(Member member, Room room) {
        this.member = member;
        this.room = room;
    }

    //== 생성 메서드 == //
    public static RoomMember createRoomMember(Member member, Room room) {
        return  RoomMember.builder()
                .member(member)
                .room(room)
                .build();
    }
}
