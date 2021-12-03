package com.douzone.pingpong.repository.member;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.Team;
import com.douzone.pingpong.domain.member.TestMember;
import com.douzone.pingpong.mapper.TestMemberMapper;
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

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

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

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
