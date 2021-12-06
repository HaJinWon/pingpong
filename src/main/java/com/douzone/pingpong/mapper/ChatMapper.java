package com.douzone.pingpong.mapper;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper extends GenericMapper<ChatDto, Chat> {

}
