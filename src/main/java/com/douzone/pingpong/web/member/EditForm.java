package com.douzone.pingpong.web.member;

import com.douzone.pingpong.domain.member.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter @Setter
public class EditForm {
    private Long id;
    private String name;
    private MultipartFile imageFile;

    private MemberStatus status;
    private String avatar;
}
