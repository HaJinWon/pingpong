package com.douzone.pingpong.domain.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("room")
@Getter
@Setter
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 1651894651651487L;

    private String roomId;
    private String title;

    public static ChatRoom create(String title) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.title = title;
        return chatRoom;
    }
}
