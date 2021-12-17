package com.douzone.pingpong.service.team;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.domain.team.Team;
import com.douzone.pingpong.repository.team.TeamRepository;
import com.douzone.pingpong.service.room.RoomService;
import com.douzone.pingpong.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TeamServiceTest {
    @Autowired TeamService teamService;
    @Autowired RoomService roomService;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberService memberService;



    @Test
    public void 팀생성() throws Exception {
        // given
        Member member = Member.builder().name("진영").build();

        // when
        Long teamId = teamService.createTeam("teamName", 1L);
        Team team = teamRepository.findById(teamId);

        // then
        Assertions.assertThat(teamId).isEqualTo(team.getId());
    }

    @Test
    public void 초대요청() throws Exception {
        // given
        Member member = Member.builder().name("진영").build();

        Long teamId = teamService.createTeam("teamName", 1L);

        // when
        teamService.inviteMember(teamId, 5L);

        // then

    }

    @Test
    public void 팀수락() throws Exception {
        // given
        Member member = Member.builder()
                .name("진영")
                        .build();

        TeamMember teamMember = TeamMember.createTeamMember(member);
        Team team = Team.createTeam("hi", teamMember);

        // when
        teamRepository.acceptTeam(team.getId(), member.getId());

        // then
    }

    @Test
    public void 단체톡방찾기() throws Exception {
        // given


        // when



        // then
    }
}