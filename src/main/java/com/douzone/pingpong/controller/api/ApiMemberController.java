package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.PartnerProfileResponse;
import com.douzone.pingpong.controller.api.dto.member.*;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.util.FileStore;
import com.douzone.pingpong.util.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.security.SessionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/api/member")
public class ApiMemberController {
    private final MemberService memberService;
    private final FileStore fileStore;

    /**
     * 회원가입
     * 클라이언트로 부터 CreateMemberRequest에 정의된 필드정보를 받아옴
     * 클라이언트에게 CreateMemberResponse에 정의된 필드를 반환
     * @return : memberId
     */
    @PostMapping()
    public JsonResult saveMember(
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
        return JsonResult.success(memberId);
    }

    /**
     * 회원 로그인
     * @return 리턴없음
     */
    @PostMapping("/login")
    public LoginMemberResponse loginMember(
            @RequestBody @Valid LoginMemberRequest request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) {

//        if (bindingResult.hasErrors()) {
//            return new LoginMemberResponse();
//        }
        Member loginMember = memberService.login(request.getEmail(), request.getPassword());

        if (loginMember == null){
            throw new IllegalStateException("일치멤버없음");
        }
        HttpSession session = httpRequest.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
        return new LoginMemberResponse(loginMember);
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // 세션 날림
        }
        return "logout Complete";
    }

    /**
     * 회원 정보 수정
     * @return : memberId, name
     */
    @PatchMapping("/edit")
    public JsonResult editMember(
                @Login Member loginMember,
                @RequestBody UpdateMemberRequest request) throws IOException {
        Long memberId = loginMember.getId();

        UploadFile imageFile = fileStore.storeFile(request.getImageFile());
        UpdateMemberDto updateMemberDto =
                new UpdateMemberDto(memberId, request.getName(), request.getPhone(), request.getCompany(), request.getStatus(), imageFile);

        //멤버 업데이트 하기
        memberService.update(updateMemberDto);

        // 업데이트한 멤버 조회 (JSON 으로 리턴해주기 위해서)
        memberService.findMember(memberId);

        return JsonResult.success(new UpdateMemberResponse(memberId, request.getName()));
    }

    /**
     *  회원 정보 수정 페이지 ( 원래 정보를 띄우기 위한 메서드)
     */
    @GetMapping("/edit")
    public JsonResult userUpdate(@Login Member loginMember){
        Member member = memberService.findMember(loginMember.getId());
        return JsonResult.success(member);
    }

    /**
     * (1). 팀에 소속된 멤버 검색 - 본인이 포함되어 조회됨 (teamId 필요)
     * ❌ 사용하지 않음. 혹시 몰라서 안지우는중 ❌
     */
    @GetMapping("/team/v2/{teamId}")
    public List<MemberDto> findByTeamMembers(
            @PathVariable Long teamId
    ) {
        List<Member> members = memberService.findByTeamMembers(teamId);
        List<MemberDto> result = members.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());
        return result;
    }
    /**
     * (2). 팀에 소속된 멤버 검색 - 본인제외 (teamId, memberId 필요)
     * 사용 : NavLeft 팀에 소속된 멤버 리스트, 1대1 대화방 만들때
     */
    @GetMapping("/team/{teamId}")
    public List<MemberDto> findByTeamMembers(
            @PathVariable Long teamId,
            @Login Member loginMember
    ) {
        Long memberId = loginMember.getId();

        List<Member> members = memberService.findByTeamMembers(memberId, teamId);
        List<MemberDto> result = members.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());
        return result;
    }


    /**
     * 상대방 프로필 조회
     * 대화방에서 상대방 프로필 사진 클릭시 나오는 화면
     * ( Avatar, Status, Name, 1대1대화방만들기버튼 )
     */
    @GetMapping("/{partnerId}")
    public PartnerProfileResponse partnerProfile(
            @PathVariable Long partnerId,
            @Login Member loginMember
    ) {
        Member member = memberService.findMember(partnerId);
        return new PartnerProfileResponse(member);
    }

    /**
     *
     *  이메일 중복확인 검사
     */
    @GetMapping("/emailcheck/{email}")
    public JsonResult joinEmailCheck(@PathVariable("email") String email){
        Member member = memberService.checkEmail(email);

        return JsonResult.success(member);
    }

    /**
     *  회원 ID 찾기
     *  require : name, phone
     */
    @PostMapping("/findId")
    public JsonResult findId(@RequestBody FindMemberEmail findMemberId){
        System.out.println("99999999999999999999999999999999999");
        String name = findMemberId.getName();
        String phone = findMemberId.getPhone();
        System.out.println("name:"+name+"phone"+phone);
        Member member = memberService.findMemberEmail(name,phone);

        return JsonResult.success(member);
    }

}


