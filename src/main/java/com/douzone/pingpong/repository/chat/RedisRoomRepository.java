package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.pubsub.RedisSubscriber;
import com.douzone.pingpong.domain.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
@Slf4j
public class RedisRoomRepository {

    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, Room> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<Long, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<Room> findAllRoom() {
        List<Room> result = opsHashChatRoom.values(CHAT_ROOMS);
        Collections.reverse(result);
        return result;

    }

    public Room findRoomById(Long id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public Room createChatRoom(Room room) {
        opsHashChatRoom.put(CHAT_ROOMS, room.getId(), room);
        return room;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(Long roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(String.valueOf(roomId));
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(Long roomId) {
        ChannelTopic topic = topics.get(roomId);
        return topic;
    }
}
