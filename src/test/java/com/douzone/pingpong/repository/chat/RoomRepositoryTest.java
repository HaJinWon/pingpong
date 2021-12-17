package com.douzone.pingpong.repository.chat;

import com.douzone.pingpong.repository.room.RoomRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomRepositoryTest {

    @Autowired
    RoomRepository roomRepository;

    /**
     * public Member (String email, String password, String name, String phone, String company, LocalDateTime date) {
     *         this.email = email;
     *         this.password = password;
     *         this.name = name;
     *         this.phone = phone;
     *         this.company = company;
     *         this.date = date;
     *     }
     * @throws Exception
     */



}