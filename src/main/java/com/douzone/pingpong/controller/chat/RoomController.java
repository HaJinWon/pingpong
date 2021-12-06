package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.mapper.ChatsMapper;
import com.douzone.pingpong.security.argumentresolver.Login;
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

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chats")
@Transactional(readOnly = true)
public class RoomController {
    private final RoomService roomService;
    private final ChatService chatService;

    //  채팅 리스트 화면
    @GetMapping("/rooms")
    public String room(Model model) {
        List<Room> rooms = roomService.findRooms();
        log.info("rooms::::{}",rooms);
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomForm", new RoomForm());
        return "chats/roomList";
    }

    // 채팅방 생성
    @PostMapping("/room")
    @Transactional
    public String createRoom(@ModelAttribute RoomForm roomForm,
                             @Login Member loginMember) {
        roomService.createRoom(loginMember.getId(), roomForm.getTitle());
        return "redirect:/rooms";
    }

    // 채팅방 입장 화면
    @GetMapping("/room/{roomId}")
    public String roomDetail(Model model,
                             @PathVariable Long roomId,
                             @Login Member loginMember) {
//        List<ChatDto> chatList = chatService.loadChat(roomId);  챗 리스트 DTO 로반환
        List<Chat> chatList = chatService.loadChat(roomId);
        Room room = roomService.findRoom(roomId);
        model.addAttribute("loginMember", loginMember.getName());
        model.addAttribute("loginMemberId", loginMember.getId());
        model.addAttribute("room", room);
        model.addAttribute("chatList", chatList);
        return "chats/room";
    }


    // 특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public Room roomInfo(@PathVariable Long roomId) {
//        return roomService.findRoom(roomId);
//    }

}



