package com.douzone.pingpong.service.chat;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.repository.chat.ChatRepository;
import com.douzone.pingpong.repository.chat.RoomRepository;
import com.douzone.pingpong.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
