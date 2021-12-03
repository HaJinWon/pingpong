package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.repository.chat.RoomRepository;
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

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "chats/room";
    }
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
    public String createRoom(@ModelAttribute RoomForm roomForm) {
        Room room = Room.create(roomForm.getTitle());
        roomService.createRoom(room);
        return "chats/roomList";
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId", roomId);
        return "chats/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable Long roomId) {
        return roomService.findRoom(roomId);
    }

}



