package com.douzone.pingpong.service.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.ChatRepository;
import com.douzone.pingpong.repository.chat.RoomRepository;
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
    public Chat createChat(Long roomId, Long memberId, String message) {

        Room room = roomRepository.findRoomById(roomId);
        Member member = memberRepository.findById(memberId);
        Chat chat = Chat.createChat(room, member, message);

        return chatRepository.save(chat);
    }

    public List<Chat> loadChat (Long roomId) {
        return chatRepository.findChatsByRoomId(roomId);
    }
}
