package com.douzone.pingpong.controller.part;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.Team;
import com.douzone.pingpong.domain.post.Comment2;
import com.douzone.pingpong.domain.post.Part;
import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.domain.team.Team2;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.fileupload.FileuploadService;
import com.douzone.pingpong.service.part.PartService;
import com.douzone.pingpong.service.team.TeamService;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/{teamId:(?!assets$|images$).*}")
public class PartController {
    
    @Autowired
    private PartService partService;

    @Autowired
    private FileuploadService fileuploadService;

    @Autowired
    private TeamService teamService;

    // 팀 페이지 불러오기 ( 통합 )
    @ResponseBody
    @RequestMapping({"/{partId}/{postId}","/{partId}",""})
    public HashMap<String,Object> teamPage(@PathVariable("teamId") Long teamId,
                                           @PathVariable(value="partId", required = false) Long partId,
                                           @PathVariable(value="postId", required = false) Long postId){
        System.out.println("teamPage");
        if(partId==null && postId==null){
            System.out.println("/1");
            List<Map<String, Object>> teamInfo = teamService.getTeamInfo(teamId);
            List<Part2> partList = partService.getPartList(teamId);
            partId = partService.getFirstPartId(teamId);
            List<Map<String,Object>> postList = partService.getPostList(partId);

            HashMap<String,Object> map = new HashMap<>();
            map.put("teamInfo",teamInfo);
            map.put("partList",partList);
            map.put("postList",postList);

            return map;

        } else if(partId != null && postId == null){
            System.out.println("/1/1");
            List<Map<String, Object>> teamInfo = teamService.getTeamInfo(teamId);
            List<Part2> partList = partService.getPartList(teamId);
            List<Map<String,Object>> postList = partService.getPostList(partId);

            HashMap<String,Object> map = new HashMap<>();
            map.put("teamInfo",teamInfo);
            map.put("partList",partList);
            map.put("postList",postList);

            return map;
        } else{
            System.out.println("/1/1/1");
            List<Map<String, Object>> teamInfo = teamService.getTeamInfo(teamId);
            List<Part2> partList = partService.getPartList(teamId);
            List<Map<String,Object>> postList = partService.getPostList(partId);
            List<Map<String,Object>> commentList = partService.getCommentList(postId);

            HashMap<String,Object> map = new HashMap<>();
            map.put("teamInfo",teamInfo);
            map.put("partList",partList);
            map.put("postList",postList);
            map.put("commentList",commentList);

            return map;
        }
    }

    //해당 팀 아이디 part 조회
    @ResponseBody
    @RequestMapping("/part/list")
    public HashMap<String,Object> getPartList(@PathVariable("teamId") String teamId){
        System.out.println("partList controller");
        Long tId = Long.parseLong(teamId);
        List<Part2> list = partService.getPartList(tId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("partList",list);
        for(int i=0; i<list.size();i++){
            System.out.println(list.get(i).getPart_id());
        }
        return map;
    }

    //새 파트 추가
    @RequestMapping("/part/add")
    public String addPart(@PathVariable("teamId") Long teamId, String partName ){

        partService.addPart(teamId,partName);
        return "home";
    }

    //파트 삭제
    @RequestMapping("/part/del/{partId}")
    public String delPart(@PathVariable("partId") Long partId){

        partService.delPart(partId);
        return "home";
    }
    /*====================================  게시글  ============================================*/

    /*
    // 게시글 목록 불러오기
    @ResponseBody
    @RequestMapping("/{partId}")
    public HashMap<String,Object> getPostList(@PathVariable("partId") Long partId){
        System.out.println("getPostList");
        List<Map<String,Object>> list = partService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);

        return map;
    }
    */
    // 게시글 삭제
    @RequestMapping("/post/del/{postId}")
    public void delPost(@PathVariable("postId") Long postId){
        partService.delPost(postId);
    }

    // 게시글 작성 페이지 이동
    @GetMapping("{partId}/post/write")
    public String movePostWrite(){

        return "";
    }

    // 게시글 작성
    @PostMapping("/{partId}/post/write")
    public String writePost(@PathVariable("partId") Long partId, @Login Member authUser,
                         String title, String contents /*, MultipartFile file, MultipartFile image*/) throws FileUploadException {
        System.out.println("addPost");
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

        partService.addPost(vo);

        return "home";

    }

    // 게시글 업데이트 페이지로 이동
    @ResponseBody
    @GetMapping("/{postId}/post/update")
    public HashMap<String, Object> movePostUpdatePage(@PathVariable("postId") Long postId){

        Post2 postVo = partService.getPostById(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postVo",postVo);

        return map;

    }
    
    //게시글 업데이트
    @PostMapping("/{postId}/post/update")
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
        return "home";
    }

    // 게시글 검색 리스트 가져오기
    @ResponseBody
    @GetMapping("/postsearch")
    public HashMap<String,Object> searchPost(@PathVariable("teamId") Long teamId, String keyword, String partId){

        List<Map<String,Object>> list = partService.searchPost(keyword,partId,teamId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("searchPostList",list);
        return map;
    }

    // 게시글 읽음 확인
    @GetMapping("/post/read/{postId}")
    public String readPost(@Login Member authUser, @PathVariable("postId") Long postId){

        partService.readPost(authUser.getId(),postId);
        //partService.readPost(2L,postId);

        return "home";
    }

    // 게시물 읽은 사람 정보 가져오기
    @ResponseBody
    @GetMapping("/post/getReadMemberList/{postId}")
    public HashMap<String,Object> getReamMember(@PathVariable("postId") Long postId){

        List<Map<String,Object>> list = partService.getPostReadMemberList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("readMemberList",list);

        return map;

    }

    /*====================================  댓글  ============================================*/

    // 해당 게시글 댓글 리스트 불러오기
    @ResponseBody
    @GetMapping("/{postId}/comment")
    public HashMap<String,Object> getCommentList(@PathVariable("postId") Long postId){
        List<Map<String,Object>> list = partService.getCommentList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("commentList",list);

        return map;
    }

    //새 댓글 작성
    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable("postId") Long postId, @Login Member authUser, String contents){

        Comment2 vo = new Comment2();
        vo.setContents(contents);
        vo.setPost_id(postId);
        //vo.setMember_id(1L);

        vo.setMember_id(authUser.getId());
        partService.addComment(vo);

        return "home";
    }

    //댓글 삭제
    @GetMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){

        partService.deleteComment(commentId);

        return "home";
    }
}
