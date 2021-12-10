package com.douzone.pingpong.web.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class JoinForm {
    //@NotBlank
    //@Email(message = "이메일 형식이여야 합니다.")
    private String email;

    //@NotBlank
    //@Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
    //        message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;

    //@NotBlank
    //@Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
    //      message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password2;

    //@NotBlank
    private String name;

    private String phone;
    private String company;

//    @AssertTrue(message = "입력한 비밀번호가 서로 다릅니다.")
    public boolean isPasswordEqual() {
        return password.equals(password2);
    }
}
