package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.part.addPartRequest;
import com.douzone.pingpong.domain.part.Part2;
import com.douzone.pingpong.util.JsonResult;
import com.douzone.pingpong.service.part.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/part")
@CrossOrigin("*")
public class ApiPartController {
    private final PartService partService;

    //해당 팀 아이디 part 조회
    @ResponseBody
    @GetMapping("/{teamId}")
    public JsonResult getPartList(@PathVariable("teamId") String teamId){

        Long tId = Long.parseLong(teamId);
        List<Part2> list = partService.getPartList(tId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("partList",list);

        return JsonResult.success(map);
    }


    //새 파트 추가
    @ResponseBody
    @PostMapping("/{teamId}")
    public JsonResult addPart(@PathVariable("teamId") Long teamId, @RequestBody addPartRequest request ){
        String partName = request.getPartName();
        partService.addPart(teamId,partName);
        return JsonResult.success("success");

    }

    //파트 삭제
    @ResponseBody
    @DeleteMapping("/{partId}")
    public JsonResult delPart(@PathVariable("partId") Long partId){
        partService.delPart(partId);
        return JsonResult.success("success");
    }
}

