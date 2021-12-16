package com.douzone.pingpong.controller.file;

import com.douzone.pingpong.controller.api.dto.member.UpdateMemberDto;
import com.douzone.pingpong.controller.api.dto.member.UpdateMemberRequest;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.file.FileService;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.util.FileStore;
import com.douzone.pingpong.web.member.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileStore fileStore;
    private final FileService fileService;
    private final MemberService memberService;

    @GetMapping("/file")
    public String newFile(@ModelAttribute EditForm form) {
        return "file/image-form";
    }

    @ResponseBody
    @PostMapping("/file")
    public String uploadFile(@ModelAttribute EditForm editForm,
                             @Login Member loginMember)
            throws IOException {

        UploadFile profileImage = fileStore.storeFile(editForm.getImageFile());
        fileService.saveFile(profileImage);

        UpdateMemberDto updateMemberDto =
                new UpdateMemberDto(editForm.getName(), editForm.getStatus(), profileImage.getFilePath());


        log.info("request={}", editForm);
        memberService.update(loginMember.getId(), updateMemberDto);

        return "success";
    }
}
