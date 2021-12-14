package com.douzone.pingpong.controller.api.dto.chatroom;

import com.douzone.pingpong.domain.chat.RoomMember;
import lombok.Data;

@Data
public class RoomMemberDto {
    private Long memberId;

    public RoomMemberDto(RoomMember roomMember) {
        this.memberId = roomMember.getMember().getId();
    }
}
