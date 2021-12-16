package com.douzone.pingpong.controller.api.dto.member;

import com.douzone.pingpong.domain.member.Include;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.domain.team.Team;
import lombok.Data;

@Data
public class InviteMemberDto {

    private Long teamId;
    private String teamName;
    private Include include;

    public InviteMemberDto(TeamMember teamMember) {
        this.teamId = teamMember.getTeam().getId();
        this.teamName = teamMember.getTeam().getName();
        this.include = teamMember.getInclude();
    }
}
