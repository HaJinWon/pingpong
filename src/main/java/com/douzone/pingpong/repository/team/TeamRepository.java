package com.douzone.pingpong.repository.team;

import com.douzone.pingpong.domain.member.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    private final EntityManager em;

    public void saveTeam(Team team) {
        em.persist(team);
    }
}
