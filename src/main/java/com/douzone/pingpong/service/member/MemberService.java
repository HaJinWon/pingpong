package com.douzone.pingpong.service.member;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.member.MemberRepository;
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
public class MemberService {
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Transactional
    public void join(Member member) {
        memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 로그인 메서드 : 실패시 null 반환
     */
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
        return member;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void editMember(EditMemberDto editMemberDto) {
        Member findMember = em.find(Member.class, editMemberDto.getId());
        log.info("findMember ::: {}", findMember);
        findMember.updateMember(editMemberDto.getName(), editMemberDto.getStatus(), editMemberDto.getAvatar());
    }


}
