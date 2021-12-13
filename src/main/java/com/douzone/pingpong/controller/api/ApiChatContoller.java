package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/chat")
public class ApiChatContoller {
    private final ChatService chatService;

    /**
     * 채팅기록 리스트
     */
    @GetMapping("/{roomId}")
    public List<ChatDto> loadChats(
            @PathVariable Long roomId
    ) {
        List<Chat> chatList = chatService.loadChat(roomId);
        List<ChatDto> result = chatList.stream()
                .map(o -> new ChatDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 메시지 삭제하기
     * -> DB에서 지우는게 아닌 메시지내용만 "삭제된 메시지 입니다"로 Update
     * 메시지 "1개"를 지우는 로직
     */
    @PatchMapping("/{chatId}")
    public void deleteChat(
            @PathVariable Long chatId
    ) {
        chatService.deleteChat(chatId);
    }



}
