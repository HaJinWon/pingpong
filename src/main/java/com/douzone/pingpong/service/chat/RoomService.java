package com.douzone.pingpong.service.chat;


import com.douzone.pingpong.domain.chat.ChatRoom;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.chat.RoomMember;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.RedisRoomRepository;
import com.douzone.pingpong.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoomService {
    private final MemberRepository memberRepository;
//    private final RoomRepository roomRepository;
    private final RedisRoomRepository redisRoomRepository;
//
//    public List<Room> findRooms() {
//        return roomRepository.findAllRoom();
////        return redisRoomRepository.findAllRoom();
//    }
//
//    @Transactional
//    public ChatRoom createRoom(Long memberId, String roomTitle) {
//
//        Member member = memberRepository.findById(memberId);
//
//        Room room = Room.create(roomTitle);
//        RoomMember roomMember = RoomMember.createRoomMember(member,room);
//        roomRepository.createChatRoom(room, roomMember);
////        return redisRoomRepository.createChatRoom(roomTitle);
//    }
//
//    public Room findRoom(String roomId) {
//        return roomRepository.findRoomById(roomId);
////            return redisRoomRepository.findRoomById(roomId);
//    }
//
//    public List<Room> findRooms() {
//        return roomRepository.findAllRoom();
////        return redisRoomRepository.findAllRoom();
//    }


    // Redis사용

    public List<ChatRoom> findRooms() {
        return redisRoomRepository.findAllRoom();
    }

    @Transactional
    public ChatRoom createRoom(Long memberId, String roomTitle) {

        Member member = memberRepository.findById(memberId);

        Room room = Room.create(roomTitle);
        RoomMember roomMember = RoomMember.createRoomMember(member,room);
        return redisRoomRepository.createChatRoom(roomTitle);
    }

    public ChatRoom findRoom(String roomId) {
            return redisRoomRepository.findRoomById(roomId);
    }
}
