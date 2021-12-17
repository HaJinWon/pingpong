package com.douzone.pingpong.service.chat;

import com.douzone.pingpong.repository.room.RoomRepository;
import com.douzone.pingpong.service.room.RoomService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomServiceTest {
    @Autowired
    RoomService roomService;
    @Autowired RoomRepository roomRepository;
    @Autowired EntityManager em;


}