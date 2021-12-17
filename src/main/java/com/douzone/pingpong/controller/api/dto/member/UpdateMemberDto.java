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
    private UploadFile image;

    public UpdateMemberDto(Long memberId, String name, String phone, String company, MemberStatus status, UploadFile image) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.company = company;
        this.status = status;
        this.image = image;
    }
}
