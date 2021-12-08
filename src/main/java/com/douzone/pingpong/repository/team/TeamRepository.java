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

    @Autowired
    private SqlSession sqlSession;

    //팀에 멤버 초대
    public void inviteMember(String teamId, Long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("userId",userId);

        sqlSession.insert("team.inviteMember",map);
    }

    public void saveTeam(Team team, TeamMember teamMember) {
        em.persist(team);
        em.persist(teamMember);
    }

    public List<Member> findUser(String userName) {
        return sqlSession.selectList("team.findUser",userName);
    }

    public void teamExit(String teamId, Long memberId) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("memberId",memberId);
        sqlSession.delete("team.exitTeam",map);
    }


    public List<Map<String, Object>> getTeamInfo(Long teamId) {
        return sqlSession.selectList("getTeamInfo",teamId);
    }
}
