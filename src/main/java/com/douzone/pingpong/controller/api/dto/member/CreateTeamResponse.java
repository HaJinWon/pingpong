package com.douzone.pingpong.controller.api.dto.member;

import lombok.Data;

@Data
public class CreateTeamResponse {
    private Long teamId;
    public CreateTeamResponse(Long teamId) {
        this.teamId = teamId;
    }
}
