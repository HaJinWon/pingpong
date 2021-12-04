package com.douzone.pingpong.controller.part;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Part;
import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.fileupload.FileuploadService;
import com.douzone.pingpong.service.part.PartService;
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

    //해당 아이디 part 조회
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

    // 게시글 목록 불러오기
    @ResponseBody
    @RequestMapping("/{partId}")
    public HashMap<String,Object> getPostList(@PathVariable("partId") Long partId){
        System.out.println("getPostList");
        List<Post2> list = partService.getPostList(partId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postList", list);

        return map;
    }

    // 게시글 삭제
    @RequestMapping("/post/del/{postId}")
    public void delPost(@PathVariable("postId") Long postId){
        partService.delPost(postId);
    }

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
        vo.setPart_id(partId);
        vo.setImage(imageUrl);
        //vo.setMember_id(authUser.getId());

        partService.addPost(vo);

        return "home";

    }

    @ResponseBody
    @GetMapping("/{postId}/post/update")
    public HashMap<String, Object> movePostUpdatePage(@PathVariable("postId") Long postId){

        Post2 postVo = partService.getPostById(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("postVo",postVo);

        return map;

    }


    @PostMapping("/{postId}/post/update")
    public String postUpdate(@PathVariable("postId") Long postId, String title, String contents
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
        //vo.setMember_id(authUser.getId());
        partService.updatePost(vo);
        return "home";
    }
    /*====================================  댓글  ============================================*/

    @ResponseBody
    @GetMapping("/{postId}/comment")
    public HashMap<String,Object> getCommentList(@PathVariable("postId") Long postId){
        List<Object> list = partService.getCommentList(postId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("commentList",list);

        return map;
    }
}
