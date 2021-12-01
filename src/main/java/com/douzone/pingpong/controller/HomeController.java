package com.douzone.pingpong.controller;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.web.SessionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
		// git TEST...
        // git TEST2 ...33333  권한READ일때
        return "home";
    }
    
    // 하경훈 테세ㅡ트ㅏㅏㅏㅏㅏ222222222222222222
}
