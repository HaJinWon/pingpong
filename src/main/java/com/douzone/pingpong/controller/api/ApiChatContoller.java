package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import com.douzone.pingpong.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
