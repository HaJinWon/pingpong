package com.douzone.pingpong.controller.api.dto.member;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Data;

@Data
public class UpdateMemberDto {
    private String name;
    private MemberStatus status;
    private String avatar;

    public UpdateMemberDto(String name, MemberStatus status, String avatar) {
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }
}
