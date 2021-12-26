package com.douzone.pingpong.domain.chat;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    private String avatar;

    public ChatDto(Chat chat) {
        chatId = chat.getId();
        roomId = chat.getRoom().getId();
        message = chat.getMessage();
        sender = chat.getMember().getName();
        senderId = chat.getMember().getId();
        date = chat.getDate();
//        avatar = chat.getMember().getAvatar();
        avatar = chat.getMember().getAvatar();
    }

}
