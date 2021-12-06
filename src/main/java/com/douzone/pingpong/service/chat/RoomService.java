package com.douzone.pingpong.service.chat;


import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.chat.RoomMember;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.RedisRoomRepository;
import com.douzone.pingpong.repository.chat.RoomRepository;
import com.douzone.pingpong.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
//@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final RedisRoomRepository redisRoomRepository;

    public List<ChatRoom> findRooms() {
//        return roomRepository.findAllRoom();
        return redisRoomRepository.findAllRoom();
    }

    @Transactional
    public ChatRoom createRoom(Long memberId, String roomTitle) {

        Member member = memberRepository.findById(memberId);

        Room room = Room.create(roomTitle);
        RoomMember roomMember = RoomMember.createRoomMember(member,room);
        return redisRoomRepository.createChatRoom(roomTitle);
//        roomRepository.createChatRoom(room, roomMember);  stomp db저장

//        return room.getId();
    }

    public ChatRoom findRoom(String roomId) {
//        return roomRepository.findRoomById(roomId);
            return redisRoomRepository.findRoomById(roomId);
    }
}
