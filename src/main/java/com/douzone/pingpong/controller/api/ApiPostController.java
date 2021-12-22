package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.post.UpdatePostRequest;
import com.douzone.pingpong.controller.api.dto.post.WritePostRequest;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.part.Part;
import com.douzone.pingpong.domain.post.Post;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.domain.post.PostDto;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.service.part.PartService;
import com.douzone.pingpong.service.post.PostService;
import com.douzone.pingpong.util.FileStore;
import com.douzone.pingpong.util.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@CrossOrigin("*")
@Slf4j
public class ApiPostController {
    private final PostService postService;
    private final MemberService memberService;
    private final PartService partService;
    private final FileStore fileStore;

    // 게시글 목록 불러오기
    @GetMapping("/{partId}")
    public JsonResult getPostList(@PathVariable("partId") Long partId){

        List<Map<String,Object>> list = postService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);
        return JsonResult.success(map);
    }

    // 게시글 작성
    @PostMapping("/{partId}")
    public JsonResult writePost(
            @PathVariable("partId") Long partId,
            @Login Member loginMember,
            @RequestBody WritePostRequest request) throws IOException {
        Member member = memberService.findMember(loginMember.getId());
        Part part = partService.getPart(partId);

        postService.writePost(request, member, part);

        return JsonResult.success("success");
    }

    // 게시글 업데이트 페이지로 이동
    @GetMapping("/update/{postId}")
    public JsonResult movePostUpdatePage(@PathVariable("postId") Long postId){

        Post post = postService.findPostById(postId);
        PostDto postDto = new PostDto(post);

        return JsonResult.success(postDto);
    }

    //게시글 업데이트
    @PatchMapping("/update/{postId}")
    public JsonResult postUpdate(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request,
            @Login Member loginMember){

        Post post = postService.findPostById(postId);

        postService.editPost(post, request.getTitle(), request.getContents(), request.getThumbnail());

        return JsonResult.success("success");
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public JsonResult delPost(@PathVariable("postId") Long postId){
        postService.delPost(postId);
        return JsonResult.success("success");
    }

    /**
     *  게시글 검색 리스트 가져오기
     *  select 박스로 partId 불러와서 검색할 API
     */
    @GetMapping("/search/{teamId}")
    public JsonResult searchPost(@PathVariable("teamId") Long teamId, String keyword, String partId){
        if(partId==null){
            partId ="";
        }
        List<Map<String,Object>> list = postService.searchPost(keyword,partId,teamId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("searchPostList",list);
        return JsonResult.success(map);
    }

    // 게시글 읽음 확인
    @GetMapping("/read/{postId}")
    public JsonResult readPost(@Login Member loginMember, @PathVariable("postId") Long postId){
        postService.readPost(loginMember.getId(),postId);
        return JsonResult.success("success");
    }

    // 게시물 읽은 사람 정보 가져오기
    @GetMapping("/getReadMemberList/{postId}")
    public JsonResult getReamMember(@PathVariable("postId") Long postId){
        List<Map<String,Object>> list = memberService.getPostReadMemberList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("readMemberList",list);

        return JsonResult.success("map");
    }
}
