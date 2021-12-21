package com.douzone.pingpong.service.member;

import com.douzone.pingpong.controller.api.dto.member.UpdateMemberDto;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
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
public class MemberService {
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 로그인 메서드 : 실패시 null 반환
     */
    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .stream()
                .filter(member -> member.getPassword().equals(password))
                .findAny().orElse(null);
    }

    @Transactional
    public Long update(UpdateMemberDto updateMemberDto) {
        Member findMember = em.find(Member.class, updateMemberDto.getMemberId());

        log.info("findMember:{}", findMember);

        findMember.updateMember(updateMemberDto);
        return findMember.getId();
    }

    public List<Member> findByTeamMembers(Long teamId) {
        return memberRepository.findByTeamMembers(teamId);
    }

    public List<Member> findByTeamMembers(Long memberId, Long teamId) {
        return memberRepository.findByTeamMembers(memberId, teamId);
    }

    public List<Member> invitationList (Long memberId) {
        return memberRepository.invitationList(memberId);
    }



    public Member checkEmail(String email) {
        return memberRepository.checkEmail(email);
    }

    public Member getUpdateUser(Long id) {
        return memberRepository.getUpdateUser(id);
    }

    public List<Map<String, Object>> getPostReadMemberList(Long postId) {
        return memberRepository.getPostReadMemberList(postId);
    }

    public Member findEmailByInfo(String name, String phone) {
        return memberRepository.findEmailByInfo(name, phone);
    }

    public Member findMemberEmail(String name, String phone) {
        return memberRepository.findMemberEmail(name,phone);
    }
}
