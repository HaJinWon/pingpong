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

    public void save(Member member) {
        System.out.println("reposi save");
        System.out.println(member.getEmail());
        em.persist(member);
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

//    public Member findByEmail(String email) {
//        return em.createQuery("select m from Member m where m.email=:email", Member.class)
//                .getSingleResult();
//    }

    public Optional<Member> findByEmail(String email) {
        Optional<Member> member = Optional.ofNullable(em.createQuery("select m from Member m where m.email=:email", Member.class)
                        .setParameter("email", email)
                        .getSingleResult());
        return member;
    }

    public Member findByEmailAndPassword (String email, String password) {
        Member member = em.createQuery("select m from Member m where m.email=:email and m.password=:password", Member.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
        return member;
    }

    public List<Member> findByTeamMembers(Long teamId) {
        return em.createQuery("select distinct m from Member m" +
                        " join fetch m.teamMembers tm" +
                        " join fetch tm.team t" +
                        " where t.id = :teamId",Member.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

    public Member checkEmail(String email) {
        return sqlSession.selectOne("member.checkEmail",email);
    }

    public Member getUpdateUser(Long id) {
        return sqlSession.selectOne("member.getUpdateUser",id);
    }
}
