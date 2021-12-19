package com.douzone.pingpong.controller.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final SimpMessageSendingOperations messagingTemplate;

    // stomp 테스트 화면
    @GetMapping("/alarm/stomp")
    public String stompAlarm() {
        return "/stomp";
    }

    @MessageMapping("/{memberId}")
    public void message(@DestinationVariable("memberId") Long memberId) {
        messagingTemplate.convertAndSend("/sub/" + memberId, "alarm socket connection completed.");
    }
}


