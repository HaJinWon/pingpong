package com.douzone.pingpong.service.chat;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.ChatRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ChatServiceTest {
    @Autowired private ChatRepository chatRepository;
    @Autowired private ChatService chatService;

    @Test
    public void 채팅기록로드() throws Exception {
        // given
//        Room room = Room.builder()
//                .id(2L)
//                .title("테스트 대화방").build();
//        Member member
//                = Member.builder().name("진영이").build();
//        member.setId(10L);
//
//        Chat chat = Chat.createChat(room, member, "hello");
//        Chat chat2 = Chat.createChat(room, member, "hello");
//        chatRepository.save(chat);
//        chatRepository.save(chat2);


        // when
        List<Chat> results = chatRepository.findChatsByRoomId(10L);

        // then
        Assertions.assertThat(results.size()).isEqualTo(3);
    }
}