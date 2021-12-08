package com.douzone.pingpong.service.member;

import com.douzone.pingpong.controller.api.dto.UpdateMemberRequest;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Transactional
    public Long join(Member member) {

        memberRepository.save(member);
        return member.getId();
    }

    // 회원 체크 (이메일 중복 체크)
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();
        if (findMember != null) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
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

    @Transactional
    public void update(Long memberId, UpdateMemberRequest request) {
        Member findMember = em.find(Member.class, memberId);
        findMember.updateMember(request.getName(), request.getStatus(), request.getAvatar());
    }

    public List<Member> findByTeamMembers(Long teamId) {
        return memberRepository.findByTeamMembers(teamId);
    }
}
