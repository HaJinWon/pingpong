package com.douzone.pingpong.controller.api.dto.chatroom;

import lombok.Data;

@Data
public class NoticeResponse {
    private Long roomId;
    private String notice;

    public NoticeResponse(Long roomId, String notice) {
        this.roomId=roomId;
        this.notice=notice;
    }
}
