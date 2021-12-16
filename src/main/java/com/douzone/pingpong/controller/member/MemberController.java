package com.douzone.pingpong.controller.member;


import com.douzone.pingpong.controller.api.dto.member.MemberDto;
import com.douzone.pingpong.controller.api.dto.member.UpdateMemberDto;
import com.douzone.pingpong.controller.api.dto.member.UpdateMemberRequest;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.MemberStatus;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.file.FileService;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.util.FileStore;
import com.douzone.pingpong.web.member.JoinForm;
import com.douzone.pingpong.web.member.LoginForm;
import com.douzone.pingpong.web.SessionConstants;
import com.douzone.pingpong.web.member.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class MemberController {
    private final MemberService memberService;
    private final FileStore fileStore;
    private final FileService fileService;

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute @Valid LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request
                        ) {

        if (bindingResult.hasErrors()) {
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
        model.addAttribute("joinForm", new JoinForm());
        return "members/joinForm";
    }

    @PostMapping("/members/new")
    public String join(@ModelAttribute @Valid JoinForm joinForm,
                       BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "members/joinForm";
//        }
        System.out.println(joinForm.getEmail());
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
    @GetMapping("/members/edit")
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

    @ResponseBody
    @PostMapping("/members/edit")
    public String uploadFile(@ModelAttribute UpdateMemberRequest request,
                             @Login Member loginMember)
            throws IOException {

        UploadFile profileImage = fileStore.storeFile(request.getAvatar());
        fileService.saveFile(profileImage);

        UpdateMemberDto updateMemberDto =
                new UpdateMemberDto(request.getName(), request.getStatus(), profileImage.getFilePath());


        log.info("request={}", request);
        memberService.update(loginMember.getId(), updateMemberDto);

        return "success";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws IOException
    {
        return new UrlResource("file:" + fileStore.getPathToday(filename));
    }


}
