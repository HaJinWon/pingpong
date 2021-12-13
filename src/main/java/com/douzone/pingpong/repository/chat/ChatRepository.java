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
        em.persist(chat);
        return chat;
    }

    public Chat findById(Long chatId) {
        return em.find(Chat.class, chatId);
    }

    public List<Chat> findChatsByRoomId(Long roomId) {
        return em.createQuery("select c from Chat c" +
                                " join fetch c.room r" +
                                " join fetch c.member m" +
                                " where r.id = :roomId")
                .setParameter("roomId", roomId)
                .getResultList();
    }
}
