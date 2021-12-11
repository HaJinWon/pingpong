package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Comment2;
import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.dto.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.fileupload.FileuploadService;
import com.douzone.pingpong.service.part.PartService;
import com.douzone.pingpong.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class ApiTeamController {
    private final PartService partService;

    private final FileuploadService fileuploadService;

    private final TeamService teamService;

    // 팀 페이지 불러오기 ( 통합 )
    @RequestMapping({"/{teamId:(?!assets$|images$).*}/{partId}/{postId}",
                     "/{teamId:(?!assets$|images$).*}/{partId}",
                     "/{teamId:(?!assets$|images$).*}"})
    public HashMap<String,Object> teamPage(@PathVariable("teamId") Long teamId,
                                           @PathVariable(value="partId", required = false) Long partId,
                                           @PathVariable(value="postId", required = false) Long postId){

        if(partId==null && postId==null){

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

            List<Map<String, Object>> teamInfo = teamService.getTeamInfo(teamId);
            List<Part2> partList = partService.getPartList(teamId);
            List<Map<String,Object>> postList = partService.getPostList(partId);

            HashMap<String,Object> map = new HashMap<>();
            map.put("teamInfo",teamInfo);
            map.put("partList",partList);
            map.put("postList",postList);

            return map;
        } else{

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

    // 팀 초대
    @PostMapping("/invite/{teamId}")
    public String invite(@PathVariable("teamId") String teamId, @RequestBody List<Long> userId){

        for(int i =0; i<userId.size();i++){
            teamService.inviteMember(teamId,userId.get(i));
        }
        return "success";
    }

    // 팀 초대장 수락
    @GetMapping("/accept/{teamId}")
    public String acceptTeam(@PathVariable("teamId") Long teamId, @Login Member authUser){
        teamService.acceptTeam(teamId,authUser.getId());
        return "success";
    }

    // 로그인 사용자가 속한 팀 정보 불러오기
    @GetMapping("/list")
    public JsonResult getTeamList(@Login Member authUser){

        List<Map<String, Object>> teamList = teamService.getTeamList(authUser.getId());
        HashMap<String,Object> map = new HashMap<>();
        map.put("teamList",teamList);
        return JsonResult.success(map);
    }

    //팀 나가기
    @GetMapping("/team/exit/{teamId}")
    public JsonResult teamExit(@PathVariable("teamId") String teamId, @Login Member member){
        teamService.teamExit(teamId,member.getId());
        return JsonResult.success("success");
    }

    // 전체 유저 검색 우리팀에 속해있는 유저 제외
    @GetMapping("/searchUser/{teamId}")
    public JsonResult findUser( @PathVariable("teamId") Long teamId, @RequestBody String userName){
        List<Map<String, Object>> list = teamService.findUser(userName,teamId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("findUserList",list);
        return JsonResult.success(map);
    }
}
