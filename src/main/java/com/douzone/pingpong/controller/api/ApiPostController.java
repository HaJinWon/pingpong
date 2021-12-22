package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.post.UpdatePostRequest;
import com.douzone.pingpong.controller.api.dto.post.WritePostRequest;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Post;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.domain.post.PostDto;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.service.post.PostService;
import com.douzone.pingpong.util.FileStore;
import com.douzone.pingpong.util.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
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
public class ApiPostController {
    private final PostService postService;
    private final MemberService memberService;
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

        UploadFile imageFile = fileStore.storeFile(request.getImageFile());
        List<UploadFile> attachFiles = fileStore.storeFiles(request.getAttachFiles());

        Post post = Post.writePost( request.getTitle(),
                                    request.getContents(),
                                    imageFile,
                                    attachFiles);
        postService.writePost(post);

        return JsonResult.success("success");
    }

    // 게시글 업데이트 페이지로 이동
    @GetMapping("/update/{postId}")
    public JsonResult movePostUpdatePage(@PathVariable("postId") Long postId){

        Post2 postVo = postService.getPostById(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postVo",postVo);

        return JsonResult.success(map);
    }

    //게시글 업데이트
    @PatchMapping("/update/{postId}")
    public JsonResult postUpdate(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request,
            @Login Member loginMember
            /*,String title, String contents,*/
            /*, MultipartFile file, MultipartFile image*/){

        Post2 post = new Post2();
        post.setTitle( request.getTitle());
        post.setContents( request.getContents());
        post.setPost_id(postId);
        postService.updatePost(post);

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
