//package com.douzone.pingpong.controller.file;
//
//import com.douzone.pingpong.controller.api.dto.member.UpdateMemberDto;
//import com.douzone.pingpong.domain.file.UploadFile;
//import com.douzone.pingpong.domain.member.Member;
//import com.douzone.pingpong.domain.post.Post;
//import com.douzone.pingpong.security.argumentresolver.Login;
//import com.douzone.pingpong.service.file.FileService;
//import com.douzone.pingpong.service.member.MemberService;
//import com.douzone.pingpong.service.post.PostService;
//import com.douzone.pingpong.util.FileStore;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.util.UriUtils;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.charset.StandardCharsets;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class FileController {
//    private final FileStore fileStore;
//    private final PostService postService;
//
//
//
////    @GetMapping("/file")
////    public String newFile(@ModelAttribute EditForm form) {
////        return "file/image-form";
////    }
////
////    @PostMapping("/file")
////    public String uploadFile(@ModelAttribute EditForm editForm,
////                             RedirectAttributes redirectAttributes,
////                             @Login Member loginMember)
////            throws IOException {
////
////        UploadFile imageFile = fileStore.storeFile(editForm.getImageFile());
////        fileService.saveFile(imageFile);
////
////        UpdateMemberDto updateMemberDto =
////                new UpdateMemberDto(editForm.getName(), editForm.getStatus(), imageFile);
////
////
////        log.info("request={}", editForm);
////        Long memberId = memberService.update(loginMember.getId(), updateMemberDto);
////
////        redirectAttributes.addAttribute("memberId", memberId);
////
////        return "redirect:/member/{memberId}";
////    }
////
////    @GetMapping("/member/{memberId}")
////    public String member(@PathVariable Long memberId, Model model) {
////        Member member = memberService.findMember(memberId);
////
////        log.info("memberName:{}", member.getName());
////        log.info("memberImage:{}", member.getUploadFile());
////        log.info("memberImage:{}", member.getUploadFile().getFilePath());
////
////        model.addAttribute("member", member);
////        return "file/member-view";
////    }
//
//    @ResponseBody
//    @GetMapping("/images/{filename}")
//    public Resource downloadImage(@PathVariable String filename) throws
//            IOException {
//        return new UrlResource("file:" + fileStore.getFullPath(filename));
//    }
//
//    @GetMapping("/attach/{itemId}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId)
//            throws IOException {
//
//        // 포스트 클릭 -> 포스트 ID로 하나의 포스트에 접근
//        Post post = postService.getPostById(postId);
//
//        // 서버에 이름으로 저장된 파일명 가져옴  Ex)631f-2462fa-3g3f-5315d.png
//        String storeFileName = post.getAttachFile().getStoreFileName();
//
//        // 사용자가 업로드한 파일명 가져옴      Ex) apple.png
//        String uploadFileName = post.getAttachFile().getUploadFileName();
//
//
//        // 디비에 저장된 파일명으로 소스접근   ex) /Users/jinyoung/project/pingpong/file/631f-2462fa-3g3f-5315d.png
//        UrlResource resource = new UrlResource("file:" +
//                fileStore.getFullPath(storeFileName));
//
//        log.info("uploadFileName={}", uploadFileName);
//
//        // 파일명 한글도 가능하게
//        String encodedUploadFileName = UriUtils.encode(uploadFileName,
//                StandardCharsets.UTF_8);
//
//
//        /**
//         * 다운로드 가능하게함
//         * 1. HTTP요청에 헤더부분을 설정하고
//         * 2. body에는 위에서 저장한 resource 설정
//         * 3. Response 요청
//         *
//         *
//         * HTTP 요청메시지 ex)
//         * Content-Disposition: attachment; filename="apple.png"
//         *
//         * body : resource (진짜파일)
//         */
//        String contentDisposition = "attachment; filename=\"" +
//                encodedUploadFileName + "\"";
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//        }
//    }
