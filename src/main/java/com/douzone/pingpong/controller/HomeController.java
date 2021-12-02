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
<<<<<<< HEAD
		// bracnhXPTMXM
        return "home";
=======
        return "/";
>>>>>>> 4465905d7cd7d8fa20cb7e7e311d9ffeadb0f3da
    }
}


