package com.douzone.pingpong.controller.api.dto;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class  UpdateMemberResponse {
    private Long id;
    private String name;

    public UpdateMemberResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
