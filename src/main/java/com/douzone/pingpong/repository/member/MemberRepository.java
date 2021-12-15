package com.douzone.pingpong.repository.member;

import com.douzone.pingpong.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    private final SqlSession sqlSession;

    // 멤버 저장
    public void save(Member member) {
        em.persist(member);
    }

    // 멤버 찾기 (멤버 ID)
    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    // 멤버 찾기 (이메일)
    public Optional<Member> findByEmail(String email) {
        Optional<Member> member = Optional.ofNullable(em.createQuery("select m from Member m where m.email=:email", Member.class)
                        .setParameter("email", email)
                        .getSingleResult());
        return member;
    }

    // 팀에 속한 멤버 검색
    public List<Member> findByTeamMembers(Long teamId) {
        return em.createQuery("select distinct m from Member m" +
                        " join fetch m.teamMembers tm" +
                        " join fetch tm.team t" +
                        " where t.id = :teamId",Member.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

    public List<Member> findByTeamMembers(Long memberId, Long teamId) {
        return em.createQuery("select distinct m from Member m" +
                        " join fetch m.teamMembers tm" +
                        " join fetch tm.team t" +
                        " where t.id = :teamId" +
                        "   and m.id <> :memberId",Member.class)
                .setParameter("teamId", teamId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public Member checkEmail(String email) {
        return sqlSession.selectOne("member.checkEmail",email);
    }

    public Member getUpdateUser(Long id) {
        return sqlSession.selectOne("member.getUpdateUser",id);
    }
}
