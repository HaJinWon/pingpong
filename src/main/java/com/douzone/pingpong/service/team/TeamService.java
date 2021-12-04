package com.douzone.pingpong.service.team;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.Team;
import com.douzone.pingpong.repository.member.MemberRepository;
import com.douzone.pingpong.repository.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final EntityManager em;

    @Transactional
    public void createTeam(Team team) {
        teamRepository.saveTeam(team);
    }

    // 맴버초대 서비스
    public void inviteMember(String teamId, Long userId){
        teamRepository.inviteMember(teamId,userId);
    }

    public List<Member> findUser(String userName) {
        List<Member> list = teamRepository.findUser(userName);
        return  list;
    }

    public void teamExit(String teamId, Long memberId) {

        teamRepository.teamExit(teamId,memberId);
    }
}
