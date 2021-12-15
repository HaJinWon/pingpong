package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.chatroom.NoticeRequest;
import com.douzone.pingpong.controller.api.dto.chatroom.NoticeResponse;
import com.douzone.pingpong.controller.api.dto.chatroom.RoomDto;
import com.douzone.pingpong.controller.api.dto.chatroom.RoomListResponse;
import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.chat.ChatService;
import com.douzone.pingpong.service.chat.RoomService;
import com.douzone.pingpong.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/room")
@CrossOrigin("*")
public class ApiRoomController {
    private final RoomService roomService;
    private final MemberService memberService;



    /**
     * 팀에 속한 모든 대화방 출력
     * loginMemberId, TeamId 를 사용하여 사용자가 선택한 팀에 대한 모든 대화방을 출력
     * JSON 반환 : 대화방ID, 대화방타이틀
     *
     */
    @GetMapping("/{teamId}")
    public RoomListResponse roomList(
            @PathVariable Long teamId,
            @Login Member loginMember
    ) {
//        Long memberId = loginMember.getId();
        Long memberId = 2L;
        List<Room> rooms = roomService.findRoomsByTeamId(memberId, teamId);
        List<Room> roomList =rooms.stream().filter(room ->
                                room.getTeam().getId().equals(teamId))
                                .collect(Collectors.toList());


        return new RoomListResponse(roomList, memberId);
    }

    /**
     * 1대1 대화방 만들기
     * 본인ID, 상대방ID, 팀Id 필요
     * 1. 본인ID로 대화방 생성 ( team_member 에도 Insert)
     * 2. 파트너 아이디로 생성된 대화방에 참여 ( team_member에 Insert)
     */
    @PostMapping("/create/{teamId}/{partnerId}")
    public void connectRoom (
            @PathVariable Long partnerId,
            @PathVariable Long teamId,
            @Login Member loginMember
    ) {
        Long memberId = loginMember.getId();
//        Long memberId = 8L;

        // 대화 상대방 조회
        Member partner = memberService.findMember(partnerId);

        // 대화방 생성 ( 대화방이름 : 상대방이름 )
        Room room = roomService.createRoom(memberId, teamId, partner.getName());

        // 파트너가 만들어진 대화방에 참여
        roomService.enterRoom(room.getId(), partnerId);
    }

    /**
     * 공지사항 등록하기
     * roomId를 가지고 공지사항을 업데이트. 최초 등록시에도 UPDATE하는 로직
     */
    @PatchMapping("/notice/{roomId}")
    public NoticeResponse notice(
            @PathVariable Long roomId,
            @RequestBody NoticeRequest noticeRequest
            ) {
        roomService.updateNotice(roomId, noticeRequest.getNotice());
        return new NoticeResponse(roomId);
    }

    /**
     * 대화방 나가기
     */
    @DeleteMapping("/{roomId}")
    public void exitRoom(
            @PathVariable Long roomId,
            @Login Member loginMember
    ) {
        roomService.exitRoom(loginMember.getId(), roomId);
    }


    /**
     * 채팅방 만들기
     * 1. DB에 저장
     * 2. Redis Hash에 저장
     * ❌ 사용하지 않음. 혹시 몰라서 안지우는중 ❌
     * JSON 반환 : 대화방ID, 대화방타이틀
     */
//    @PostMapping("/{teamId}")
    public RoomDto createRoom(
            @RequestBody String title,
            @PathVariable Long teamId,
            @Login Member loginMember
    ) {
        Room room = roomService.createRoom(loginMember.getId(), teamId, title);
        return new RoomDto(room, 1L);
    }

    /**
     * 존재하는 대화방에 "참여" (팀에 수락시 해당 단체방에 참여)
     * team_member (다대다 매핑 테이블) Insert
     * ❌ 사용하지 않음. 혹시 몰라서 안지우는중 ❌
     */
//    @GetMapping("/enter/{roomId}")
    public Room enterRoom(@PathVariable Long roomId,
                          @Login Member loginMember) {
        Room room = roomService.findRoom(roomId);
        return room;
    }

    /**
     * 대화방에 입장
     * => 대화방을 Click 한 상황, 즉 클라이언트는 대화방을 보고 있음 => pub/sub 필요
     *  ❌ 사용하지 않음. 혹시 몰라서 안지우는중 ❌
     */
//    @GetMapping("/{roomId}")
    public String seeRoom(
            @PathVariable Long roomId
    ) {
        Room room = roomService.findRoom(roomId);
        return "clickRoom";
    }
}
