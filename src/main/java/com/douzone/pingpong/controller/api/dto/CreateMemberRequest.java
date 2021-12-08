package com.douzone.pingpong.controller.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateMemberRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password2;

    private String phone;

    private String company;

    @AssertTrue(message = "입력한 비밀번호가 서로 다릅니다.")
    public boolean isPasswordEqual() {
        return password.equals(password2);
    }
}
