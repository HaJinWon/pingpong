package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.domain.room.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.room.RedisRoomRepository;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.chat.ChatService;
import com.douzone.pingpong.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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


    //채팅 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
        return redisRoomRepository.findAllRoom();
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    @ResponseBody
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

        return "chatgg";
    }

    // 특정 대화방 반환
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable Long roomId) {
        return redisRoomRepository.findRoomById(roomId);
    }

}



