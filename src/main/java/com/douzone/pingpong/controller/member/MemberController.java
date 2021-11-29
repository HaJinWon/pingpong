package com.douzone.pingpong.controller.member;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.web.JoinForm;
import com.douzone.pingpong.web.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        Member loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
        // 로그인 성공 처리
        return "redirect:/members/loginForm";
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


}
