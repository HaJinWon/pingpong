package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;


@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(Chat message) {
        if (Chat.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getMember().getName() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chats/room/" + message.getId(), message);
    }
}











/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */
