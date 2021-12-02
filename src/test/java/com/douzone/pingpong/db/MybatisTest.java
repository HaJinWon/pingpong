package com.douzone.pingpong.db;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TestMember;
import com.douzone.pingpong.mapper.MemberMapper;
import com.douzone.pingpong.mapper.TestMemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@org.mybatis.spring.boot.test.autoconfigure.MybatisTest
public class MybatisTest {
    @Autowired
    TestMemberMapper testMemberMapper;

    @Test
    public void save() {
        TestMember testMember = new TestMember(1L, "testname");
        testMemberMapper.save(testMember);
        TestMember findMember = testMemberMapper.findById(testMember.getId());
        Assertions.assertThat(findMember.getId()).isEqualTo(1L);
    }
}
