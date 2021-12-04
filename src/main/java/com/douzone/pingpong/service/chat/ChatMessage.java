package com.douzone.pingpong.domain.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessage {
    private String roomId;
    private String sender;
    private String message;

    @Builder
    public ChatMessage(String roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
