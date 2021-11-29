package com.douzone.pingpong.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinForm {
    private String email;
    private String password;
    private String password2;
    private String name;
    private String phone;
    private String company;
}
