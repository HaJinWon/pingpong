package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.service.part.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/part")
@CrossOrigin("*")
public class ApiPartController {

    @Autowired
    private PartService partService;

    //해당 팀 아이디 part 조회
    @ResponseBody
    @RequestMapping("/list/{teamId:(?!assets$|images$).*}")
    public HashMap<String,Object> getPartList(@PathVariable("teamId") String teamId){
        System.out.println("partList controller");
        Long tId = Long.parseLong(teamId);
        List<Part2> list = partService.getPartList(tId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("partList",list);

        return map;
    }


    //새 파트 추가
    @ResponseBody
    @PostMapping("/add/{teamId:(?!assets$|images$).*}")
    public String addPart(@PathVariable("teamId") Long teamId, @RequestBody String partName ){

        partService.addPart(teamId,partName);

        return "success";
    }

    //파트 삭제
    @ResponseBody
    @RequestMapping("/del/{partId}")
    public String delPart(@PathVariable("partId") Long partId){

        partService.delPart(partId);
        return "success";
    }
}

