package com.douzone.pingpong.controller.team;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.team.Team;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.team.TeamService;
import com.douzone.pingpong.web.team.CreateTeamForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//@Controller
@Slf4j
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/team/create")
    public String createForm(Model model) {
        model.addAttribute("createTeamForm", new CreateTeamForm());
        return "team/createTeamForm";
    }


    @PostMapping("/team/create")
    public String create(@Login Member loginMember,
                         @ModelAttribute CreateTeamForm createTeamForm) {

        Team team = Team.builder()
                .name(createTeamForm.getName())
                .date(LocalDateTime.now())
                .host(loginMember.getId()).build();

        teamService.createTeam(team);
        return "redirect:/";
    }

    /**
     * 1. 팀생성
     * 2. 멤버 검색 (전부, keyword)
     * 3. 생성된 팀에 들어가기 (초대장 송수신)
     * 4. 팀나가기
     */




}
