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
        chatDto.setDate(LocalDateTime.now());
        redisPublisher.publish(redisRoomRepository.getTopic(chatDto.getRoomId()), chatDto);
        log.info("member::::{}", chatDto.getAvatar());
        chatService.saveChat(chatDto.getRoomId(), chatDto.getSenderId(), chatDto.getMessage());
    }

    @MessageMapping("/chat/enter")
    public void enter(@RequestBody RoomDto roomDto) {
        redisRoomRepository.enterChatRoom(roomDto.getRoomId());
    }

    @MessageMapping("/invite/{teamId}")
    public String inviteMembers(@DestinationVariable Long teamId,
                                @RequestBody RequestInviteTeam request) {

        log.info("들어왔다고~~~~~~~~~~~~~~~~~~");
        request.getMembers().forEach(memberId -> {
            Member member = memberService.findMember(memberId);
            teamService.inviteMember(teamId, memberId);
            messageTemplate.convertAndSendToUser(member.getName(), "/sub/pingpong/"+memberId , new ResponseInvite(teamId, memberId));
        });
        return "success";
    }
}

/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */