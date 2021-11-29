package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
public class ChatRepository {
    // 채팅방(topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    //구독 처리
    private final RedisSubscriber redisSubscriber;

    //redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Room> opsHashChatRoom;

    //채팅방의 대화 메시지를 발행하기 위한 redis topic 정보, 서버별로 매치되는 topic정보를
    // map에 넣어 roomId로 찾을 수 있도록 한다
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private  void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<Room> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public Room findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장
    public Room createChatRoom(String name) {
        Room room = Room.create(name);
        opsHashChatRoom.put(CHAT_ROOMS, String.valueOf(room.getId()) , room);
        return room;
    }

    // 채팅방 입장 : redis에 topic을 만들고 pub/sub통신을 위한 리스너 설정
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}