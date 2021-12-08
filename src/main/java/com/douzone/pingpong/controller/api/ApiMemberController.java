package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.*;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.web.SessionConstants;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/api")
public class ApiMemberController {
    private final MemberService memberService;

    /**
     * 회원가입
     * 클라이언트로 부터 CreateMemberRequest에 정의된 필드정보를 받아옴
     * 클라이언트에게 CreateMemberResponse에 정의된 필드를 반환
     * @return : memberId
     */
    @PostMapping("/members")
    public CreateMemberResponse saveMember(
            @RequestBody @Valid CreateMemberRequest request
    ) {
        System.out.println("test");

        Member member = Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .phone(request.getPhone())
                .company(request.getCompany())
                .date(LocalDateTime.now())
                .build();
        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    /**
     * 회원 로그인
     * @return 리턴없음
     */
    @PostMapping("/members/login")
    public LoginMemberResponse loginMember(
            @RequestBody @Valid LoginMemberRequest request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) {
        System.out.println("로그인 컨트롤ㄹ러");
        if (bindingResult.hasErrors()) {
            return new LoginMemberResponse();
        }
        Member loginMember = memberService.login(request.getEmail(), request.getPassword());

        HttpSession session = httpRequest.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
        return new LoginMemberResponse();
    }


    /**
     * 회원 정보 수정
     * @return : memberId, name
     */
    @PostMapping("/members/edit")
    public UpdateMemberResponse editMember(
                @Login Member loginMember,
                @RequestBody UpdateMemberRequest request) {
        Long memberId = loginMember.getId();
        memberService.update(memberId, request);
        memberService.findMember(memberId);

        return new UpdateMemberResponse(memberId, request.getName());
    }

    /**
     * 팀에 소속된 멤버 검색
     * @return
     */
    @GetMapping("/members/{teamId}")
    public List<MemberDto> findByTeamMembers(
            @PathVariable Long teamId
    ) {
        List<Member> members = memberService.findByTeamMembers(teamId);
        List<MemberDto> result = members.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());
        return result;
    }

//    @GetMapping("/members/v2/{teamId}")
//    public List<MemberDto> find ByTeamMembersV2(
//            @PathVariable Long teamId
//    ) {
//        List<Member> members = memberService.findByTeamMembers(teamId);
//        List<MemberDto> result = members.stream()
//                .map(m -> new MemberDto(m))
//                .collect(Collectors.toList());
//        return result;
//
//    }

}

