package com.douzone.pingpong.controller.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginMemberRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
