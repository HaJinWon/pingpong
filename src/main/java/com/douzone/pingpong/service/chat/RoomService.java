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
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class RoomService {
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final TeamRepository teamRepository;
    private final RedisRoomRepository redisRoomRepository;

    public List<Room> findRoomsByTeamId(Long teamId) {
        return roomRepository.findByTeam(teamId);
    }

    // 팀에 속한 모든 채팅방 찾기
    public List<Room> findRoomsByTeamId(Long memberId, Long teamId) {
        return roomRepository.findRoomsByTeamId(memberId, teamId);
    }

    @Transactional
    public Room createRoom(Long memberId, Long teamId, String roomTitle) {
        Member member = memberRepository.findById(memberId);
        return saveRoom(roomTitle, teamId, member);
    }

    /**
     * 대화방을 저장하는 메서드
     * 1. DataBase에 저장
     * 2. 레디스 Cache에 저장
     */
    private Room saveRoom(String roomTitle,Long teamId ,Member member) {
        Team team = teamRepository.findById(teamId);

        // 다대다 매핑 테이블 RoomMember 생성
        RoomMember roomMember = RoomMember.mappingRoomMember(member);
        log.info("roommaker:::{}",member.getId());

        // 대화방 생성
        Room room = Room.createRoom(roomMember, team, roomTitle);

        roomRepository.createChatRoom(room);                // 디비에 저장
        return redisRoomRepository.createChatRoom(room);    // 레디스에 저장
    }

    @Transactional
    public void enterRoom(Long roomId, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Room room = roomRepository.findById(roomId);

        RoomMember roomMember = RoomMember.createRoomMember(member, room);
        roomRepository.enterChatRoom(roomMember);
    }

    public Room findRoom(Long roomId) {
        return roomRepository.findById(roomId);
    }

    @Transactional
    public void updateNotice(Long roomId, String notice) {
        Room room = roomRepository.findById(roomId);
        room.updateNotice(notice);
    }

    @Transactional
    public void exitRoom(Long memberId, Long roomId) {
        Room room = roomRepository.findById(roomId);

        // team_member 테이블 찾기
        RoomMember findRoomMember = room.getRoomMembers()
                        .stream().filter(
                                roomMember -> roomMember.getMember().getId().equals(memberId))
                        .findFirst().get();

        // 찾은 팀멤버를 삭제하기 team_member DELETE
        room.getRoomMembers().remove(findRoomMember);
    }
}
