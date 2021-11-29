package com.douzone.pingpong.controller.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.pubsub.RedisPublisher;
import com.douzone.pingpong.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(Chat message) {
        if (Chat.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(getRoomId(message));
            message.setMessage(message.getChatMember().getId() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(chatRoomRepository.getTopic(getRoomId(message)),message);
    }

    private String getRoomId(Chat message) {
        return String.valueOf(message.getRoom().getId());
    }
}


/**
 * 발행자 (Publisher) 역할
 * @MessageMapping을 통해 Websocket으로 들어오는 메시지를 "발행"처리
 * 클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청 -> controller처리
 * 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send
 */
