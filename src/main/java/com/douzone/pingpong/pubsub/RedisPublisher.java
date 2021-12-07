package com.douzone.pingpong.pubsub;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.service.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * Redis 발행 서비스
 * 채팅방에 입장하여 메세지를 작성하면 해당 메세지를 Redis Topic에 발행하는 기능의 서비스
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
        log.info("publishing:: {} / {}", topic.getTopic(), message);
    }
}