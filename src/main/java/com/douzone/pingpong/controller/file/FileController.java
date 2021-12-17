//package com.douzone.pingpong.controller.file;
//
//import com.douzone.pingpong.controller.api.dto.member.UpdateMemberDto;
//import com.douzone.pingpong.controller.api.dto.member.UpdateMemberRequest;
//import com.douzone.pingpong.domain.file.UploadFile;
//import com.douzone.pingpong.domain.member.Member;
//import com.douzone.pingpong.security.argumentresolver.Login;
//import com.douzone.pingpong.service.file.FileService;
//import com.douzone.pingpong.service.member.MemberService;
//import com.douzone.pingpong.util.FileStore;
//import com.douzone.pingpong.util.FileStoreBack;
//import com.douzone.pingpong.web.member.EditForm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class FileController {
//    private final FileStore fileStore;
//    private final FileService fileService;
//    private final MemberService memberService;
//
//
//
//    @GetMapping("/file")
//    public String newFile(@ModelAttribute EditForm form) {
//        return "file/image-form";
//    }
//
//    @PostMapping("/file")
//    public String uploadFile(@ModelAttribute EditForm editForm,
//                             RedirectAttributes redirectAttributes,
//                             @Login Member loginMember)
//            throws IOException {
//
//        UploadFile imageFile = fileStore.storeFile(editForm.getImageFile());
//        fileService.saveFile(imageFile);
//
//        UpdateMemberDto updateMemberDto =
//                new UpdateMemberDto(editForm.getName(), editForm.getStatus(), imageFile);
//
//
//        log.info("request={}", editForm);
//        Long memberId = memberService.update(loginMember.getId(), updateMemberDto);
//
//        redirectAttributes.addAttribute("memberId", memberId);
//
//        return "redirect:/member/{memberId}";
//    }
//
//    @GetMapping("/member/{memberId}")
//    public String member(@PathVariable Long memberId, Model model) {
//        Member member = memberService.findMember(memberId);
//
//        log.info("memberName:{}", member.getName());
//        log.info("memberImage:{}", member.getUploadFile());
//        log.info("memberImage:{}", member.getUploadFile().getFilePath());
//
//        model.addAttribute("member", member);
//        return "file/member-view";
//    }
//
//    @ResponseBody
//    @GetMapping("/images/{filename}")
//    public Resource downloadImage(@PathVariable String filename) throws
//            IOException {
//        return new UrlResource("file:" + fileStore.getFullPath(filename));
//    }
//
//}
