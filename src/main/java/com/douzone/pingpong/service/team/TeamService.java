package com.douzone.pingpong.service.team;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.domain.team.Team;
import com.douzone.pingpong.repository.chat.RoomRepository;
import com.douzone.pingpong.repository.member.MemberRepository;
import com.douzone.pingpong.repository.team.TeamRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    private final EntityManager em;

    @Transactional
    public Long createTeam(String name, Long memberId) {

        Member findMember = memberRepository.findById(memberId);

        // 다대다 테이블(TeamMember) 생성
        TeamMember teamMember = TeamMember.createTeamMember(findMember);

        // 팀 테이블 생성
        Team team = Team.createTeam(name, teamMember);

        teamRepository.saveTeam(team);
        return team.getId();
    }

    // 맴버초대 서비스
    public void inviteMember(Long teamId, Long userId){
        teamRepository.inviteMember(teamId,userId);
    }

    public List<Map<String, Object>> findUser(String userName, Long teamId) {
        List<Map<String, Object>> list = teamRepository.findUser(userName, teamId);
        return  list;
    }

    public void teamExit(String teamId, Long memberId) {

        teamRepository.teamExit(teamId,memberId);
    }


    public List<Map<String, Object>> getTeamInfo(Long teamId) {

        return teamRepository.getTeamInfo(teamId);
    }

    public List<Map<String, Object>> getTeamList(Long memberId) {
        return teamRepository.getTeamList(memberId);
    }

    @Transactional
    public void acceptTeam(Long teamId, Long memberId) {

        List<Room> roomList = roomRepository.findByTeam(teamId);
        Room groupRoom = roomList.stream().findFirst().get();
        log.info("groupRoom:{}", groupRoom);

        teamRepository.acceptTeam(teamId,memberId);
    }
}
