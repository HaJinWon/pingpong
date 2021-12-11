package com.douzone.pingpong.controller.api.dto.member;

import lombok.Data;

@Data
public class LoginMemberResponse {
    private Long memberId;

    public LoginMemberResponse(Long memberId) {
        this.memberId = memberId;
    }
}
