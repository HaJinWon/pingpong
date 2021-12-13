package com.douzone.pingpong.controller.api.dto;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Data;

@Data
public class PartnerProfileResponse {
    private Long partnerId;
    private String name;
    private String avatar;
    private MemberStatus status;


    public PartnerProfileResponse(Member member) {
        this.partnerId = member.getId();
        this.name = member.getName();
        this.avatar = member.getAvatar();
        this.status = member.getStatus();
    }
}
