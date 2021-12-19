package com.douzone.pingpong.domain.chat;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ChatDto implements Serializable{
    private static final long serialVersionUID = 1651894651651487L;
    private MessageType type;

    public enum MessageType {
        ENTER, TALK, EXIT
    }

    private Long chatId;
    private Long roomId;
    private String message;
    private String sender;
    private Long senderId;
    private LocalDateTime date;
    private String avatar;

    public ChatDto(Chat chat) {
        chatId = chat.getId();
        roomId = chat.getRoom().getId();
        message = chat.getMessage();
        sender = chat.getMember().getName();
        senderId = chat.getMember().getId();
        date = chat.getDate();
//        avatar = chat.getMember().getUploadFile();
    }
}
