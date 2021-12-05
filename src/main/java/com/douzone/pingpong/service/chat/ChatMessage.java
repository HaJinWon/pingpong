package com.douzone.pingpong.service.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Builder
public class ChatMessage {
    private Long roomId;
    private Long senderId;
    private String message;
    private String sender;

    public ChatMessage(Long roomId, Long senderId, String message, String sender) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
        this.sender = sender;
    }
}
