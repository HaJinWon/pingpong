package com.douzone.pingpong.controller.api.dto;

import com.douzone.pingpong.domain.member.Member;
import lombok.Data;

@Data
public class LoginMemberResponse {
    private Member member;

    public LoginMemberResponse(Member member){
        this.member = member;
    }
}
