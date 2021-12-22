package com.douzone.pingpong.controller.api.dto;

public class ResponseInvite {
    private Long teamId;
    private Long memberId;

    public ResponseInvite(Long teamId, Long memberId) {
        this.teamId = teamId;
        this.memberId = memberId;
    }
}
