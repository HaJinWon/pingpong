package com.douzone.pingpong.controller.api.dto.chatroom;

import com.douzone.pingpong.domain.chat.Room;
import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String title;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();;
    }
}
