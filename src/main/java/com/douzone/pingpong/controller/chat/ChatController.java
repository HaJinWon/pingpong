package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.controller.api.dto.chatroom.NoticeRequest;
import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.room.RoomDto;
import com.douzone.pingpong.pubsub.RedisPublisher;
import com.douzone.pingpong.repository.room.RedisRoomRepository;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.service.chat.ChatService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
//    private final SimpleMessageSendingOperations messagingTemplate;
    private final RedisPublisher redisPublisher;
    private final RedisRoomRepository redisRoomRepository;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatDto chatDto) {
        chatDto.setDate(LocalDateTime.now());
        redisPublisher.publish(redisRoomRepository.getTopic(chatDto.getRoomId()), chatDto);
        chatService.saveChat(chatDto.getRoomId(), chatDto.getSenderId(), chatDto.getMessage());
    }

    @MessageMapping("/chat/enter")
    public void enter(@RequestBody RoomDto roomDto) {
        redisRoomRepository.enterChatRoom(roomDto.getRoomId());
    }
}

/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */