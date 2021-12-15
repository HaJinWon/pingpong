package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.chatroom.CreateTeamRequest;
import com.douzone.pingpong.controller.api.dto.member.CreateTeamResponse;
import com.douzone.pingpong.controller.api.dto.team.RequestInviteTeam;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.util.JsonResult;
import com.douzone.pingpong.security.argumentresolver.Login;
import com.douzone.pingpong.service.chat.RoomService;
import com.douzone.pingpong.service.fileupload.FileuploadService;
import com.douzone.pingpong.service.part.PartService;
import com.douzone.pingpong.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class ApiTeamController {
    private final PartService partService;
    private final FileuploadService fileuploadService;
    private final TeamService teamService;
    private final RoomService roomService;

    /**
     * 팀 생성
     * 1. Team 생성 ( 다대다 테이블 TeamMember Insert)
     * 2. ChatRoom 생성 ( 팀에서 사용할 전체 대화방 생성 title : 팀이름)
     */
    @PostMapping("/create")
    public CreateTeamResponse create(@Login Member loginMember,
                         @RequestBody CreateTeamRequest request) {
        Long memberId = loginMember.getId();
//        Long memberId = 3L;

        // 팀생성
        Long teamId = teamService.createTeam(request.getTeamName(), memberId);

        // 단체 대화방 생성
        roomService.createRoom(memberId,teamId, request.getTeamName());
        return new CreateTeamResponse(teamId);
    }

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
            map.put("partList",partList);
            map.put("postList",postList);
            map.put("commentList",commentList);

            return map;
        }
    }

    /**
     * 초대장 보내기 (복수 가능)
     * ex)
     * {
     *     "members": [ 4, 5]
     * }
     * => 멤버ID 4,5 에게 초대장 보내기
     */
    @PostMapping("/invite/{teamId}")
    public String inviteMember( @PathVariable Long teamId,
                                @RequestBody RequestInviteTeam request){
        System.out.println("invite");
        System.out.println("request"+request.getMembers());
        request.getMembers().forEach(memberId -> teamService.inviteMember(teamId, memberId));
        return "success";
    }

    /**
     * 팀 초대장 수락하기
     * 로그인한 멤버가 초대장 수락을 누르면 호출되는 메서드
     * 1.team_member 테이블의 상태값(include)이 UPDATE됨
     * 2.팀 단체대화방에 참가됨
     *
     * PatchMapping : 멱등하다, 똑같은 값으로 업데이트 요청시 요청되지않음.
     */
    @PatchMapping("/accept/{teamId}")
    public String acceptTeam(@PathVariable("teamId") Long teamId,
                             @Login Member loginMember){
        // 해당팀의 단체대화방 ID 찾기
//        Long memberId = loginMember.getId();
        Long memberId = 5L;

        List<Room> roomList = roomService.findRoomsByTeamId(teamId);
        Room groupRoom = roomList.stream().findFirst().get();
        log.info("groupRoom:{}", groupRoom.getId());

        roomService.enterRoom(groupRoom.getId(), memberId);
        
       teamService.acceptTeam(teamId, memberId );
        return "success";
    }

    // 로그인 사용자가 속한 팀 정보 불러오기
    @GetMapping("/list")
    public JsonResult getTeamList(@Login Member loginMember){

        List<Map<String, Object>> teamList = teamService.getTeamList(loginMember.getId());
        HashMap<String,Object> map = new HashMap<>();
        map.put("teamList",teamList);
        return JsonResult.success(map);
    }

    //팀 나가기
    @GetMapping("/exit/{teamId}")
    public JsonResult teamExit(@PathVariable("teamId") Long teamId, @Login Member loginMember){
        teamService.teamExit(teamId,loginMember.getId());
        return JsonResult.success("success");
    }

    // 전체 유저 검색 우리팀에 속해있는 유저 제외
    @PostMapping("/searchUser/{teamId}")
    public JsonResult findUser( @PathVariable("teamId") Long teamId, @RequestBody String memberName){

        System.out.println(memberName);
        List<Map<String, Object>> list = teamService.findUser(memberName,teamId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("findUserList",list);
        return JsonResult.success(map);
    }
}
