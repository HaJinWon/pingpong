package com.douzone.pingpong.controller.chat.config;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class RoomController {

    private final ChatRepository chatRoomRepository;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅창 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    public Room createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

}
