package com.douzone.pingpong.service.chat;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

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
