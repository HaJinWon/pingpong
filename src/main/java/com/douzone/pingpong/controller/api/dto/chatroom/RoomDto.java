package com.douzone.pingpong.controller.api.dto.chatroom;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.chat.RoomMember;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomDto {
    private Long id;
    private String title;
    private List<RoomMemberDto> roomMembers;

    public RoomDto(Room room) {

        this.id = room.getId();
        this.title = room.getTitle();
        roomMembers = room.getRoomMembers().stream()
                .map(roomMember -> new RoomMemberDto(roomMember))
                .collect(Collectors.toList());
    }
}
