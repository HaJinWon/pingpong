package com.douzone.pingpong.domain.chat;

import com.douzone.pingpong.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatDto {
//    private Long id;
    private Member member;
//    private Room room;
    private String message;
    private LocalDateTime date;
//    private Integer notReadCount;
}
