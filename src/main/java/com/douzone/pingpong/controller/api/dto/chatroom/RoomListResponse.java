package com.douzone.pingpong.controller.api.dto.chatroom;

import com.douzone.pingpong.domain.chat.Room;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomListResponse {
    private List<RoomDto> roomDtoList;


    public RoomListResponse(List<Room> rooms, Long memberId) {
        roomDtoList = rooms.stream()
                .map(room -> new RoomDto(room, memberId))
                .collect(Collectors.toList());
    }
}





