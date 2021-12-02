package com.douzone.pingpong.controller.member;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TestMember;
import com.douzone.pingpong.mapper.MemberMapper;
import com.douzone.pingpong.mapper.TestMemberMapper;
import com.douzone.pingpong.repository.member.MemberRepository;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.member.EditMemberDto;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.web.JoinForm;
import com.douzone.pingpong.web.LoginForm;
import com.douzone.pingpong.web.SessionConstants;
import com.douzone.pingpong.web.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    /*

    private final TestMemberMapper testMemberMapper;

    @GetMapping("/test")
    public String test() {
        TestMember findMember = testMemberMapper.findById(1L);
        return "test";
    }*/

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        Model model) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return "members/loginForm";
        }
        Member loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
        // 로그인 성공 처리
        HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);   // 세션에 로그인 회원 정보 보관
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // 세션 날림
        }
        return "redirect:/";
    }

    // == 회원가입 == //
    @GetMapping("/new")
    public String JoinForm(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "members/joinForm";
    }

    @PostMapping("/new")
    public String join(@ModelAttribute JoinForm joinForm) {
        Member member = Member.builder()
                .email(joinForm.getEmail())
                .password(joinForm.getPassword())
                .name(joinForm.getName())
                .phone(joinForm.getPhone())
                .company(joinForm.getCompany())
                .date(LocalDateTime.now())
                .build();
        memberService.join(member);
        return "redirect:/";
    }

    // == 프로필수정 == //
    @GetMapping("/edit")
    public String editForm(@Login Member loginMember,
                             Model model) {
        Member findMember = memberService.findMember(loginMember.getId());

        EditForm editForm = EditForm.builder()
                .name(findMember.getName())
                .avatar(findMember.getAvatar())
                .status(findMember.getStatus()).build();

        model.addAttribute("editForm", editForm);
        return "members/editForm";
    }

    @PostMapping("/edit")
    public String edit(@Login Member loginMember,
                       @ModelAttribute EditForm editForm,
                       BindingResult bindingResult
                       ) {

        if (bindingResult.hasErrors()) {
            return "members/editForm";
        }

        Long memberId = loginMember.getId();

        EditMemberDto editMemberDto = EditMemberDto.builder()
                            .id(memberId)
                            .name(editForm.getName())
                            .avatar(editForm.getAvatar())
                            .status(editForm.getStatus())
                            .build();

        memberService.editMember(editMemberDto);
        return "redirect:/";
    }
}
