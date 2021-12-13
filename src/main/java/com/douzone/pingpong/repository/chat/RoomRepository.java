package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.chat.RoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class RoomRepository {
    private final EntityManager em;

    public List<Room> findAllRoom() {
        return em.createQuery("select r from Room r", Room.class)
                .getResultList();
    }
    /**
     * 대화방 참가자 찾기
     */
    public RoomMember findRoomMember(Long memberId) {
        return em.find(RoomMember.class, memberId);
    }


    /**
     * 팀에 속한 모든채팅방 찾기 (접속자ID, TeamId 사용)
     */
    public List<Room> findRoomsByTeamId(Long memberId, Long teamId) {
        return em.createQuery("select distinct r from Room r" +
                              " join fetch r.team t" +
                              " join fetch r.roomMembers rm" +
                              " join fetch rm.member m" +
                              " where t.id = :teamId" +
                              " and  m.id = :memberId"
                              ,Room.class  )
                .setParameter("teamId", teamId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public Room findById(Long roomId) {
        return em.find(Room.class, roomId);
    }


    /**
     * 팀에 해당하는 대화방 조회 ( 단톡방 찾을때 사용 )
     */
    public List<Room> findByTeam(Long teamId) {
        return em.createQuery("select distinct r from Room r" +
                                      " join fetch r.team t" +
                                      " where t.id = :teamId",Room.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

    /**
     * 대화방 저장 (Insert)
     * table : room, room_member
     */
    public Long createChatRoom(Room room) {
        em.persist(room);
        return room.getId();
    }

    /**
     * 존재하는 대화방에 "참여" (Insert)
     * team_member
     */
    public void enterChatRoom(RoomMember roomMember) {
        em.persist(roomMember);
    }
}

