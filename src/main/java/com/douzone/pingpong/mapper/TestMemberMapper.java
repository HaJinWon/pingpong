package com.douzone.pingpong.mapper;

import com.douzone.pingpong.domain.member.TestMember;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface TestMemberMapper {
    @Insert("insert into test_member (test_id, name) values (#{id} , #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "testId")
    public void save(TestMember testMember);

    @Select("select * from test_member where test_id = #{id}")
    public TestMember findById(Long id);
}
