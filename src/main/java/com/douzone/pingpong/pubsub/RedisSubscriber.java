package com.douzone.pingpong.pubsub;

import com.douzone.pingpong.domain.chat.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

            Chat roomMessage = objectMapper.readValue(publishMessage, Chat.class);

            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoom().getId(), roomMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}