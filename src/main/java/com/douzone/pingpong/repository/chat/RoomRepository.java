package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.domain.chat.Room;
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

    public Room findRoomById(Long roomId) {
        return em.find(Room.class, roomId);
    }

    public Long createChatRoom(Room room) {
        em.persist(room);
        return room.getId();
    }
}

