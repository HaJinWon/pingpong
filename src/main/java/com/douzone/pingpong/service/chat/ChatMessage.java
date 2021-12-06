package com.douzone.pingpong.service.chat;


import lombok.*;

import java.io.Serializable;

@Getter @Setter
public class ChatMessage implements Serializable{
    private static final long serialVersionUID = 1651894651651487L;
    private MessageType type;

    public enum MessageType {
        ENTER, TALK, EXIT
    }

    private String roomId;
    private String message;
    private String sender;


//    public ChatMessage(String roomId, Long senderId, String message, String sender, MessageType type) {
//        this.roomId = roomId;
//        this.senderId = senderId;
//        this.message = message;
//        this.sender = sender;
//        this.type = type;
//    }
}
