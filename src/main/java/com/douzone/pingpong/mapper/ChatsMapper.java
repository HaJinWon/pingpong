package com.douzone.pingpong.mapper;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.ChatDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChatsMapper extends GenericMapper<ChatDto, Chat>{
    ChatsMapper INSTANCE = Mappers.getMapper(ChatsMapper.class);

    List<ChatDto> toDoList(List<Chat> entityList);
}
