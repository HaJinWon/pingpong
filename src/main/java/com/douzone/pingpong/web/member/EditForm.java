package com.douzone.pingpong.web.member;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class EditForm {
    private Long id;
    private String name;
    private String avatar;
    private MemberStatus status;
}
