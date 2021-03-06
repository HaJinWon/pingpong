package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.controller.api.dto.ResponseInvite;
import com.douzone.pingpong.controller.api.dto.chatroom.NoticeRequest;
import com.douzone.pingpong.controller.api.dto.team.RequestInviteTeam;
import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.room.RoomDto;
import com.douzone.pingpong.pubsub.RedisPublisher;
import com.douzone.pingpong.repository.room.RedisRoomRepository;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.service.chat.ChatService;
import com.douzone.pingpong.service.member.MemberService;
import com.douzone.pingpong.service.team.TeamService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
    private final SimpMessagingTemplate messageTemplate;
    private final RedisPublisher redisPublisher;
    private final RedisRoomRepository redisRoomRepository;
    private final ChatService chatService;
    private final MemberService memberService;
    private final TeamService teamService;


    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatDto chatDto) {
        log.info("::: ChatController.message :::");
        chatDto.setDate(LocalDateTime.now());
        redisPublisher.publish(redisRoomRepository.getTopic(chatDto.getRoomId()), chatDto);
        chatService.saveChat(chatDto.getRoomId(), chatDto.getSenderId(), chatDto.getMessage());
    }

    @MessageMapping("/chat/enter")
    public void enter(@RequestBody RoomDto roomDto) {
        log.info("::: ChatController.enter :::");
        redisRoomRepository.enterChatRoom(roomDto.getRoomId());

    }

    @MessageMapping("/invite/{teamId}")
    public String inviteMembers(@DestinationVariable Long teamId,
                                @RequestBody RequestInviteTeam request) {

        request.getMembers().forEach(memberId -> {
            Member member = memberService.findMember(memberId);
            teamService.inviteMember(teamId, memberId);
            messageTemplate.convertAndSendToUser(member.getName(), "/sub/pingpong/"+memberId , new ResponseInvite(teamId, memberId));
        });
        return "success";
    }
}

/**
 * ????????? (Publisher) ??????
 * @MessageMapping??? ?????? Websocket?????? ???????????? ???????????? "??????"??????
 * ???????????????????????? prefix??? ????????? /pub/chat/message??? ?????? ?????? -> controller??????
 * ???????????? ???????????? /sub/chat/room/{roomId}??? ???????????? send
 */