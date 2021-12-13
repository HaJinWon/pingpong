package com.douzone.pingpong.controller.api.dto.chatroom;

import lombok.Data;

@Data
public class NoticeResponse {
    private Long roomId;

    public NoticeResponse(Long roomId) {
        this.roomId=roomId;
    }
}
