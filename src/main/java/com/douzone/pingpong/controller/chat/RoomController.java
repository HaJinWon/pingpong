package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.RedisRoomRepository;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.domain.chat.ChatRoom;
import com.douzone.pingpong.service.chat.ChatService;
import com.douzone.pingpong.service.chat.RoomService;
import com.douzone.pingpong.web.chat.RoomForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class RoomController {
    private final RoomService roomService;
    private final RedisRoomRepository redisRoomRepository;
    private final ChatService chatService;

    //  채팅 리스트 화면
    @GetMapping("/room")
    public String room(Model model) {
        List<Room> rooms = roomService.findRooms();

        log.info("rooms::::{}",rooms);
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomForm", new RoomForm());
        return "chat/roomList";
    }

    //채팅 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
        return redisRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public Room createRoom(@ModelAttribute RoomForm roomForm,
                             @Login Member loginMember) {
        return roomService.createRoom(loginMember.getId(), roomForm.getTitle());
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model,
                             @PathVariable Long roomId,
                             @Login Member loginMember) {
        List<Chat> result = chatService.loadChat(roomId);
        List<ChatDto> chatList = result.stream()
                .map(o -> new ChatDto(o))
                .collect(Collectors.toList());

        Room room = roomService.findRoom(roomId);
        model.addAttribute("loginMember", loginMember.getName());
        model.addAttribute("loginMemberId", loginMember.getId());
        model.addAttribute("room", room);
        model.addAttribute("chatList", chatList);     // 채팅기록 로드하기.. 에러가 너무많이뜬다 ㅠㅠ

        return "chat/room";
    }

    // 특정 대화방 반환
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable Long roomId) {
        return redisRoomRepository.findRoomById(roomId);
    }

}



