package com.douzone.pingpong.service.chat;


import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.repository.chat.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findRooms() {
        return roomRepository.findAllRoom();
    }

    @Transactional
    public Long createRoom(Room room) {
        roomRepository.createChatRoom(room);
        return room.getId();
    }

    public Room findRoom(Long roomId) {
        return roomRepository.findRoomById(roomId);

    }
}
