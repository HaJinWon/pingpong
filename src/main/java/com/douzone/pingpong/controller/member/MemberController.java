package com.douzone.pingpong.controller.member;

import com.douzone.pingpong.domain.member.Member;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute @Validated LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        Member loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());
        System.out.println("111111 = " + loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
        // 로그인 성공 처리
        HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);   // 세션에 로그인 회원 정보 보관
        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // 세션 날림
        }
        return "redirect:/";
    }

    // == 회원가입 == //
    @GetMapping("/members/new")
    public String JoinForm(Model model) {
        model.addAttribute("form", new JoinForm());
        return "members/joinForm";
    }

    @PostMapping("/members/new")
    public String join(@ModelAttribute JoinForm form) {
        Member member = new Member(form);
        memberService.join(member);
        return "redirect:/";
    }

    // == 프로필수정 == //
    @GetMapping("/members/{memberId}/edit")
    public String editForm(@PathVariable Long memberId,
                             Model model) {
        Member findMember = memberService.findMember(memberId);

        EditForm form = EditForm.builder()
                .name(findMember.getName())
                .avatar(findMember.getAvatar())
                .status(findMember.getStatus()).build();

        model.addAttribute("form", form);
        return "members/editForm";
    }




}
