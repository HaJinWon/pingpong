package com.douzone.pingpong.service.member;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditMemberDto {
    private Long id;
    private String name;
    private String avatar;
    private MemberStatus status;
}
