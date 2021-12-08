package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.pubsub.RedisPublisher;
import com.douzone.pingpong.repository.chat.RedisRoomRepository;
import com.douzone.pingpong.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final RedisRoomRepository redisRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if(ChatMessage.MessageType.ENTER.equals(message.getType())){
            redisRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(redisRoomRepository.getTopic(message.getRoomId()), message);
    }



    /*
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

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
     */
}











/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */
