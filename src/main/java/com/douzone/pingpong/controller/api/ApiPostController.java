package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Comment2;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.util.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.part.PartService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JsonResult getPostList(@PathVariable("partId") Long partId){

        List<Map<String,Object>> list = partService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);

        return JsonResult.success(map);
    }

    @RequestMapping("/listtest/{partId}")
    public JsonResult getPostListtest(@PathVariable("partId") Long partId){

        List<Map<String,Object>> list = partService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);

        return JsonResult.success(map);
    }

    // 게시글 삭제
    @RequestMapping("/del/{postId}")
    public JsonResult delPost(@PathVariable("postId") Long postId){
        partService.delPost(postId);
        return JsonResult.success("success");
    }

    // 게시글 작성
    @PostMapping("/post/write/{partId}")
    public JsonResult writePost(@PathVariable("partId") Long partId, @Login Member loginMember,
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
        vo.setMember_id(loginMember.getId());
        */
        partService.addPost(postVo);

        return JsonResult.success("success");

    }

    // 게시글 업데이트 페이지로 이동
    @GetMapping("/update/{postId}")
    public JsonResult movePostUpdatePage(@PathVariable("postId") Long postId){

        Post2 postVo = partService.getPostById(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postVo",postVo);

        return JsonResult.success(map);

    }

    //게시글 업데이트
    @PostMapping("/update/{postId}")
    public JsonResult postUpdate(@PathVariable("postId") Long postId, @RequestBody Post2 postVo/*,String title, String contents,*/, @Login Member loginMember
            /*, MultipartFile file, MultipartFile image*/){

        Post2 vo = new Post2();
        //String fileUrl = fileuploadService.restoreFile(file);
        //String imageUrl = fileuploadService.restoreFile(image);
        String fileUrl ="test수정";
        String imageUrl ="test수정";
        vo.setPost_id(postVo.getPost_id());
        vo.setTitle(postVo.getTitle());
        vo.setContents(postVo.getContents());
        vo.setFile(postVo.getFile());
        vo.setImage(postVo.getImage());
        vo.setMember_id(loginMember.getId());
        partService.updatePost(vo);
        return JsonResult.success("success");
    }

    // 게시글 검색 리스트 가져오기
    /*
        select 박스로 partId 불러와서 검색할 API
     */
    @GetMapping("/search/{teamId:(?!assets$|images$).*}")
    public JsonResult searchPost(@PathVariable("teamId") Long teamId, String keyword, String partId){
        if(partId==null){
            partId ="";
        }
        List<Map<String,Object>> list = partService.searchPost(keyword,partId,teamId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("searchPostList",list);
        return JsonResult.success(map);
    }

    // 게시글 읽음 확인
    @GetMapping("/read/{postId}")
    public JsonResult readPost(@Login Member loginMember, @PathVariable("postId") Long postId){

        partService.readPost(loginMember.getId(),postId);


        return JsonResult.success("success");
    }

    // 게시물 읽은 사람 정보 가져오기
    @GetMapping("/getReadMemberList/{postId}")
    public JsonResult getReamMember(@PathVariable("postId") Long postId){

        List<Map<String,Object>> list = partService.getPostReadMemberList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("readMemberList",list);

        return JsonResult.success("map");

    }

    /*====================================  댓글  ============================================*/

    // 해당 게시글 댓글 리스트 불러오기
    @GetMapping("/comment/{postId}")
    public JsonResult getCommentList(@PathVariable("postId") Long postId){
        List<Map<String,Object>> list = partService.getCommentList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("commentList",list);

        return JsonResult.success(map);
    }

    //새 댓글 작성
    @PostMapping("/comment/{postId}")
    public JsonResult addComment(@PathVariable("postId") Long postId, @Login Member loginMember, @RequestBody String contents){

        Comment2 vo = new Comment2();
        vo.setContents(contents);
        vo.setPost_id(postId);
        //vo.setMember_id(1L);

        vo.setMember_id(loginMember.getId());
        partService.addComment(vo);

        return JsonResult.success("success");
    }

    //댓글 삭제
    @GetMapping("/comment/delete/{commentId}")
    public JsonResult deleteComment(@PathVariable("commentId") Long commentId){

        partService.deleteComment(commentId);

        return JsonResult.success("success");
    }
}
