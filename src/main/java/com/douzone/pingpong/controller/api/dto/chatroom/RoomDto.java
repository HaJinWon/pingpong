package com.douzone.pingpong.controller.api.dto.chatroom;

import com.douzone.pingpong.domain.room.Room;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomDto {
    private Long roomId;
    private String title;
    private String notice;
    private List<RoomMemberDto> roomMembers;

    public RoomDto(Room room) {

        this.roomId = room.getId();
        this.title = room.getTitle();
        this.notice = room.getNotice();
        roomMembers = room.getRoomMembers().stream()
                .map(roomMember -> new RoomMemberDto(roomMember))
                .collect(Collectors.toList());
    }

    public RoomDto(Room room, Long memberId) {

        this.roomId = room.getId();
        this.title = room.getTitle();
        this.notice = room.getNotice();
        roomMembers = room.getRoomMembers().stream()
                .filter(roomMember -> roomMember.getMember().getId().equals(memberId))
                .map(roomMember -> new RoomMemberDto(roomMember))
                .collect(Collectors.toList());
    }
}
