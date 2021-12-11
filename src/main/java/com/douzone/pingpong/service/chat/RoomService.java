package com.douzone.pingpong.service.chat;


import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.chat.RoomMember;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.team.Team;
import com.douzone.pingpong.repository.chat.RedisRoomRepository;
import com.douzone.pingpong.repository.chat.RoomRepository;
import com.douzone.pingpong.repository.member.MemberRepository;
import com.douzone.pingpong.repository.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class RoomService {
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final TeamRepository teamRepository;
    private final RedisRoomRepository redisRoomRepository;

    public List<Room> findRooms() {
        return roomRepository.findAllRoom();
    }

    // 팀에 속한 모든 채팅방 찾기
    public List<Room> findRoomsByTeamId(Long memberId, Long teamId) {
        return roomRepository.findRoomsByTeamId(memberId, teamId);
    }

    @Transactional
    public Room createRoom(Long memberId, String roomTitle) {
        Member member = memberRepository.findById(memberId);
        return saveRoom(roomTitle, member);
    }

    /**
     * 대화방을 저장하는 메서드
     * 1. DataBase에 저장
     * 2. 레디스 구독 메서드 (대화방이 만들어지면 만든사람은 바로 구독해야하기 때문)
     * 3. 레디스 Cache에 저장
     */
    private Room saveRoom(String roomTitle, Member member) {
        Team team = teamRepository.findById(1L);

        // 다대다 매핑 테이블 RoomMember 생성
        RoomMember roomMember = RoomMember.createRoomMember(member);
        log.info("roommaker:::{}",member.getId());

        // 대화방 생성
        Room room = Room.createRoom(roomMember, team, roomTitle);

        roomRepository.createChatRoom(room);                // 디비에 저장
        redisRoomRepository.enterChatRoom(room.getId());    // 레디스 구독
        return redisRoomRepository.createChatRoom(room);    // 레디스에 저장
    }

    public void enterRoom(Long roomId) {
        redisRoomRepository.enterChatRoom(roomId);
    }

    public Room findRoom(Long roomId) {
        return roomRepository.findById(roomId);
    }
}
