package com.douzone.pingpong.domain.chat;

import com.douzone.pingpong.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_member")
@Getter @Setter
@NoArgsConstructor
@DynamicInsert
public class RoomMember implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

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

    //== 연관관계 메서드 == //
}
