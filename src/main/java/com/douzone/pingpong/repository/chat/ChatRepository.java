package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.domain.chat.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final EntityManager em;

    public Chat save(Chat chat) {
        System.out.println("chat111111 = " + chat);
        em.persist(chat);
        return chat;
    }

    public List<Chat> findChatsByRoomId(Long roomId) {
        return em.createQuery("select c from Chat c join c.room r where r.id = :roomId", Chat.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }


}
