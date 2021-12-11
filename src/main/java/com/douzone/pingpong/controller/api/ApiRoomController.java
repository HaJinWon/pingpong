package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.chatroom.RoomDto;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.chat.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/room")
public class ApiRoomController {
    private final RoomService roomService;

    /**
     * 채팅방 만들기
     * 1. DB에 저장
     * 2. Redis Hash에 저장
     * 3. Redis 구독 ( 대화방을 만든사람은 만들어진 대화방을 바로 구독)
     * JSON 반환 : 대화방ID, 대화방타이틀
     */
    @PostMapping
    public RoomDto createRoom(
            @RequestBody String title,
            @Login Member loginMember
    ) {
        Room room = roomService.createRoom(loginMember.getId(), title);
        return new RoomDto(room);
    }


    /**
     * 팀에 속한 모든 대화방 출력
     * loginMemberId, TeamId 를 사용하여 사용자가 선택한 팀에 대한 모든 대화방을 출력
     * JSON 반환 : 대화방ID, 대화방타이틀
     */
    @GetMapping("/{teamId}")
    public List<RoomDto> roomList(
            @PathVariable Long teamId,
            @Login Member loginMember
    ) {
        List<Room> rooms = roomService.findRoomsByTeamId(2L, teamId);
        List<RoomDto> roomDtoList = rooms.stream()
                .map(room -> new RoomDto(room))
                .collect(Collectors.toList());
        return roomDtoList;
    }


}
