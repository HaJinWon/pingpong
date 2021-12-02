package com.douzone.pingpong.mapper;

import com.douzone.pingpong.domain.member.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {
//    @Insert("Insert into Member(id, name, password) values (")
//    public Long save(Member member);
//
//    public List<Member> findAll();
//
//    @Select("select * from Member where member_id = #{memberId}")
//    public Member findOneTest(Long memberId);
}
