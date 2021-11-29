package com.douzone.pingpong.service;

import com.douzone.pingpong.domain.chat.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, Room> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<Room> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public Room findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}