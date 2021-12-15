package com.douzone.pingpong.repository.team;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.domain.team.Team;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    private final EntityManager em;
    private final SqlSession sqlSession;

    //팀에 멤버 초대
    public void inviteMember(TeamMember teamMember) {
        em.persist(teamMember);
    }

    public void saveTeam(Team team) {
        em.persist(team);
    }

    public List<Map<String, Object>> findUser(String userName, Long teamId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("teamId",teamId);
        return sqlSession.selectList("team.findUser",map);
    }

    public void teamExit(Long teamId, Long memberId) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("memberId",memberId);
        sqlSession.delete("team.exitTeam",map);
    }


    public List<Map<String, Object>> getTeamInfo(Long teamId) {
        return sqlSession.selectList("team.getTeamInfo",teamId);
    }

    public List<Map<String, Object>> getTeamList(Long memberId) {
        return sqlSession.selectList("team.getTeamList", memberId);
    }

    public void acceptTeam(Long teamId, Long memberId) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("memberId",memberId);
        sqlSession.update("team.acceptTeam",map);
    }

    public Team findById(Long teamId) {
        return em.find(Team.class, teamId);
    }


}
