package com.douzone.pingpong.service.chat;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.repository.chat.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomServiceTest {
    @Autowired RoomService roomService;
    @Autowired RoomRepository roomRepository;
    @Autowired EntityManager em;

    @Test
    public void 대화방생성() throws Exception {
        // given
        Room room = Room.create("xkdlxmf");

        // when
        Long roomId = roomRepository.createChatRoom(room);

        // then
        Assertions.assertThat(roomId).isEqualTo(room.getId());
    }

}