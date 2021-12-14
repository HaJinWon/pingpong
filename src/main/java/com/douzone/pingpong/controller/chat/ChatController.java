package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.pubsub.RedisPublisher;
import com.douzone.pingpong.repository.chat.RedisRoomRepository;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final RedisRoomRepository redisRoomRepository;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatDto chatDto) {
        if(ChatDto.MessageType.ENTER.equals(chatDto.getType())){
            redisRoomRepository.enterChatRoom(chatDto.getRoomId());
            chatDto.setMessage(chatDto.getSender() + "님이 입장하셨습니다.");
        }
<<<<<<< HEAD
=======

>>>>>>> 0b3b837e46cb480595438bb204584ca41eaa47f5
        log.info("!!!!!id: {} / chatDTd: {}",redisRoomRepository.getTopic(chatDto.getRoomId()),chatDto);
        redisPublisher.publish(redisRoomRepository.getTopic(chatDto.getRoomId()), chatDto);
        chatService.saveChat(chatDto.getRoomId(), chatDto.getSenderId(), chatDto.getMessage());
    }
}

/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */
