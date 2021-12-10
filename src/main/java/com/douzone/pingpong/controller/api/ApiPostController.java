package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Comment2;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.dto.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.part.PartService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    private PartService partService;

    // 게시글 목록 불러오기
    @RequestMapping("/list/{partId}")
    public HashMap<String,Object> getPostList(@PathVariable("partId") Long partId){
        System.out.println("getPostList");
        List<Map<String,Object>> list = partService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);

        return map;
    }

    @RequestMapping("/listtest/{partId}")
    public JsonResult getPostListtest(@PathVariable("partId") Long partId){
        System.out.println("getPostList");
        List<Map<String,Object>> list = partService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);

        return JsonResult.success(map);
    }

    // 게시글 삭제
    @RequestMapping("/del/{postId}")
    public String delPost(@PathVariable("postId") Long postId){
        partService.delPost(postId);
        return "success";
    }

    // 게시글 작성
    @PostMapping("/post/write/{partId}")
    public String writePost(@PathVariable("partId") Long partId, @Login Member authUser,
                            @RequestBody Post2 postVo /*, MultipartFile file, MultipartFile image*/) throws FileUploadException {
        System.out.println("addPost");
        /*
        Post2 vo = new Post2();
        //String fileUrl = fileuploadService.restoreFile(file);
        //String imageUrl = fileuploadService.restoreFile(image);
        String fileUrl ="test";
        String imageUrl ="test";
        vo.setTitle(title);
        vo.setContents(contents);
        vo.setFile(fileUrl);
        vo.setImage(imageUrl);
        vo.setMember_id(authUser.getId());
        */
        partService.addPost(postVo);

        return "success";

    }

    // 게시글 업데이트 페이지로 이동
    @GetMapping("/update/{postId}")
    public HashMap<String, Object> movePostUpdatePage(@PathVariable("postId") Long postId){

        Post2 postVo = partService.getPostById(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postVo",postVo);

        return map;

    }

    //게시글 업데이트
    @PostMapping("/update/{postId}")
    public String postUpdate(@PathVariable("postId") Long postId, String title, String contents, @Login Member authUser
            /*, MultipartFile file, MultipartFile image*/){

        Post2 vo = new Post2();
        //String fileUrl = fileuploadService.restoreFile(file);
        //String imageUrl = fileuploadService.restoreFile(image);
        String fileUrl ="test수정";
        String imageUrl ="test수정";
        vo.setPost_id(postId);
        vo.setTitle(title);
        vo.setContents(contents);
        vo.setFile(fileUrl);
        vo.setImage(imageUrl);
        vo.setMember_id(authUser.getId());
        partService.updatePost(vo);
        return "success";
    }

    // 게시글 검색 리스트 가져오기
    /*
        select 박스로 partId 불러와서 검색할 API
     */
    @GetMapping("/search/{teamId:(?!assets$|images$).*}")
    public HashMap<String,Object> searchPost(@PathVariable("teamId") Long teamId, String keyword, String partId){
        if(partId==null){
            partId ="";
        }
        List<Map<String,Object>> list = partService.searchPost(keyword,partId,teamId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("searchPostList",list);
        return map;
    }

    // 게시글 읽음 확인
    @GetMapping("/read/{postId}")
    public String readPost(@Login Member authUser, @PathVariable("postId") Long postId){

        partService.readPost(authUser.getId(),postId);
        //partService.readPost(2L,postId);

        return "success";
    }

    // 게시물 읽은 사람 정보 가져오기
    @GetMapping("/getReadMemberList/{postId}")
    public HashMap<String,Object> getReamMember(@PathVariable("postId") Long postId){

        List<Map<String,Object>> list = partService.getPostReadMemberList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("readMemberList",list);

        return map;

    }

    /*====================================  댓글  ============================================*/

    // 해당 게시글 댓글 리스트 불러오기
    @GetMapping("/comment/{postId}")
    public HashMap<String,Object> getCommentList(@PathVariable("postId") Long postId){
        List<Map<String,Object>> list = partService.getCommentList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("commentList",list);

        return map;
    }

    //새 댓글 작성
    @PostMapping("/comment/{postId}")
    public String addComment(@PathVariable("postId") Long postId, @Login Member authUser, @RequestBody String contents){

        Comment2 vo = new Comment2();
        vo.setContents(contents);
        vo.setPost_id(postId);
        //vo.setMember_id(1L);

        vo.setMember_id(authUser.getId());
        partService.addComment(vo);

        return "success";
    }

    //댓글 삭제
    @GetMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){

        partService.deleteComment(commentId);

        return "success";
    }
}
