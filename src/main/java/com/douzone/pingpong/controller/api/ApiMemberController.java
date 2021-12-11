package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.member.*;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.dto.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.web.SessionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
            @RequestBody CreateMemberRequest request
    ) {
        log.info("request {} ", request);

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
        if (bindingResult.hasErrors()) {
            log.info("Login Valid Error");
        }
        Member loginMember = memberService.login(request.getEmail(), request.getPassword());

        if (loginMember == null) {
            // 로그인 실패 로직 ( 회원정보 일치하지 않음)
        }

        HttpSession session = httpRequest.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
        return new LoginMemberResponse(loginMember.getId());
    }


    /**
     * 회원 정보 수정
     * @return : memberId, name
     */
    @PostMapping("/members/edit")
    public UpdateMemberResponse editMember(
                @Login Member loginMember,
                @RequestBody UpdateMemberRequest request) {
        System.out.println("updateForm");
        Long memberId = loginMember.getId();
        memberService.update(memberId, request);
        memberService.findMember(memberId);

        return new UpdateMemberResponse(memberId, request.getName());
    }

    /**
     *  회원 정보 수정 페이지 ( 원래 정보를 띄우기 위한 메서드)
     */
    @GetMapping("/members/edit")
    public JsonResult userUpdate(@Login Member member){
        System.out.println("getUpdateUser");
        Member updateMemeber = memberService.getUpdateUser(1L);
        //Member updateMemeber = memberService.getUpdateUser(member.getId());
        System.out.println("updateMemeber = " + updateMemeber);
        return JsonResult.success(updateMemeber);
    }

    /**
     * 팀에 소속된 멤버 검색
     * 1대1 채팅할때 사용
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


    @GetMapping("/members/emailcheck/{email}")
    public JsonResult joinEmailCheck(@PathVariable("email") String email){
        System.out.println("emailcheck");
        System.out.println("emailcheck"+email);
        Member member = memberService.checkEmail(email);

        return JsonResult.success(member);
    }
}

