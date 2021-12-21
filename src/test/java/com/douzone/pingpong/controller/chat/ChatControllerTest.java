package com.douzone.pingpong.controller.chat;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ChatControllerTest {

    @Test
    public void test() {
        LocalDateTime date = LocalDateTime.now();
        String h = date.toString();
        System.out.println("h = " + h);
    }
}