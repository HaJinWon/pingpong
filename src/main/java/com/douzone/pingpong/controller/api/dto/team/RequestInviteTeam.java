package com.douzone.pingpong.controller.api.dto.team;

import lombok.Data;

import java.util.List;

@Data
public class RequestInviteTeam {
    private List<Long> members;
}


