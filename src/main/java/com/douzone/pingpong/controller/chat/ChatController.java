package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.service.chat.ChatMessage;
import com.douzone.pingpong.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;
//    @MessageMapping(value = "/chats/enter")
//    public void enter(Chat message) {
//        message.setMessage(message.getMember().getName() + "님이 입장하셨습니다.");
//        messagingTemplate.convertAndSend("/sub/chats/room/" + 1, message);
//    }

//    @MessageMapping(value = "/chats/message")
//    public void message(Chat message) {
//        messagingTemplate.convertAndSend("/sub/chats/room/" + message.getRoom().getId(), message);
//    }

    @MessageMapping("{roomId}")
    public void test(@DestinationVariable Long roomId, ChatMessage message) {
        Chat chat = chatService.createChat(roomId, message.getSenderId(), message.getMessage());
        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(roomId)
                .senderId(chat.getMember().getId())
                .message(chat.getMessage())
                .sender(message.getSender())
                .build();
        messagingTemplate.convertAndSend("/sub/" + roomId, chatMessage);
    }
}











/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */
