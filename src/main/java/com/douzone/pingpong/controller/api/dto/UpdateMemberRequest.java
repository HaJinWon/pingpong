package com.douzone.pingpong.controller.api.dto;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Data;

@Data
public class UpdateMemberRequest {
    private String name;
    private String avatar;
    private MemberStatus status;
}
