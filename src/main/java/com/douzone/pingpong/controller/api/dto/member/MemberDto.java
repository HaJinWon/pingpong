package com.douzone.pingpong.controller.api.dto.member;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.MemberStatus;
import com.douzone.pingpong.domain.member.TeamMember;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberDto {
    private Long memberId;
    private String name;
    private MemberStatus status;

    private List<TeamMemberDto> teamMembers;

    public MemberDto(Member member) {
        memberId = member.getId();
        name = member.getName();
        status = member.getStatus();
        teamMembers = member.getTeamMembers()
                .stream().map(teamMember -> new TeamMemberDto(teamMember))
                .collect(Collectors.toList());
    }

    @Data
    static class TeamMemberDto {
        private String teamName;
        private Long hostId;

        public TeamMemberDto(TeamMember teamMember) {
            teamName = teamMember.getTeam().getName();
            hostId = teamMember.getTeam().getId();
        }
    }
}
