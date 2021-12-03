package com.douzone.pingpong.controller;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TestMember;
import com.douzone.pingpong.mapper.TestMemberMapper;
import com.douzone.pingpong.web.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        return "home";
    }
}



