package com.douzone.pingpong.controller.api.dto.member;

import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Data;

@Data
public class UpdateMemberDto {
    private Long memberId;
    private String name;
    private String phone;
    private String company;
    private MemberStatus status;
    private String avatar;

    public UpdateMemberDto(Long memberId, String name, String phone, String company, MemberStatus status, String avatar) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.company = company;
        this.status = status;
        this.avatar = avatar;
    }
}
