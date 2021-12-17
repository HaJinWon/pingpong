package com.douzone.pingpong.service.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.room.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.ChatRepository;
import com.douzone.pingpong.repository.room.RoomRepository;
import com.douzone.pingpong.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Chat saveChat(Long roomId, Long memberId, String message) {

        Room room = roomRepository.findById(roomId);
        Member member = memberRepository.findById(memberId);

        Chat chat = Chat.createChat(room, member, message);

        return chatRepository.save(chat);
    }

    public List<Chat> loadChat (Long roomId) {
        List<Chat> chatList = chatRepository.findChatsByRoomId(roomId);
        return chatList;
    }

    @Transactional
    public void deleteChat (Long chatId) {
        Chat chat = chatRepository.findById(chatId);
        chat.deleteChat();
    }
}
