package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.domain.chat.Room;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomRepositoryTest {

    @Autowired RoomRepository roomRepository;

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