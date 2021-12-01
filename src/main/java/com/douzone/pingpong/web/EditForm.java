package com.douzone.pingpong.web;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EditForm {
    private String name;
    private String avatar;
    private MemberStatus status;
}
