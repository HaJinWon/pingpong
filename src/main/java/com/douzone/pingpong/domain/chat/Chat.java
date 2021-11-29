package com.douzone.pingpong.domain.chat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Chat {
    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_member_id")
    private ChatMember chatMember;      //sender

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;                  // 대화방 정보


    private String message;
    private LocalDateTime date;

    @Column(name = "not_read_count")
    private Integer notReadCount;

    private MessageType type;

    public enum MessageType {
        ENTER, TALK, EXIT
    }

}
