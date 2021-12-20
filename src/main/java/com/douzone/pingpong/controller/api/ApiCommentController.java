package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.AddCommentRequest;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.comment.Comment2;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.comment.CommentService;
import com.douzone.pingpong.util.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class ApiCommentController {
    private final CommentService commentService;

    // 해당 게시글 댓글 리스트 불러오기
    @GetMapping("/{postId}")
    public JsonResult getCommentList(@PathVariable("postId") Long postId){
        List<Map<String,Object>> list = commentService.getCommentList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("commentList",list);

        return JsonResult.success(map);
    }

    //새 댓글 작성
    @PostMapping("/{postId}")
    public JsonResult addComment(
            @PathVariable("postId") Long postId,
            @Login Member loginMember,
            @RequestBody AddCommentRequest request){
        Comment2 vo = new Comment2();
        vo.setContents(request.getContents());
        vo.setPost_id(postId);
        //vo.setMember_id(1L);

        vo.setMember_id(loginMember.getId());
        commentService.addComment(vo);

        return JsonResult.success("success");
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public JsonResult deleteComment(@PathVariable("commentId") Long commentId){

        commentService.deleteComment(commentId);

        return JsonResult.success("success");
    }
}
