package com.douzone.pingpong.mapper;

import com.douzone.pingpong.domain.chat.Chat;
import com.douzone.pingpong.domain.chat.Chat.ChatBuilder;
import com.douzone.pingpong.domain.chat.ChatDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-05T18:20:23+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 13.0.1 (Oracle Corporation)"
)
public class ChatsMapperImpl implements ChatsMapper {

    @Override
    public ChatDto toDto(Chat e) {
        if ( e == null ) {
            return null;
        }

        ChatDto chatDto = new ChatDto();

        chatDto.setMember( e.getMember() );
        chatDto.setMessage( e.getMessage() );
        chatDto.setDate( e.getDate() );

        return chatDto;
    }

    @Override
    public Chat toEntity(ChatDto d) {
        if ( d == null ) {
            return null;
        }

        ChatBuilder chat = Chat.builder();

        chat.member( d.getMember() );
        chat.message( d.getMessage() );

        return chat.build();
    }

    @Override
    public List<ChatDto> toDtoList(List<Chat> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChatDto> list = new ArrayList<ChatDto>( entityList.size() );
        for ( Chat chat : entityList ) {
            list.add( toDto( chat ) );
        }

        return list;
    }

    @Override
    public List<Chat> toEntityList(List<ChatDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Chat> list = new ArrayList<Chat>( dtoList.size() );
        for ( ChatDto chatDto : dtoList ) {
            list.add( toEntity( chatDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(ChatDto dto, Chat entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getMember() != null ) {
            entity.setMember( dto.getMember() );
        }
        if ( dto.getMessage() != null ) {
            entity.setMessage( dto.getMessage() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
    }

    @Override
    public List<ChatDto> toDoList(List<Chat> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChatDto> list = new ArrayList<ChatDto>( entityList.size() );
        for ( Chat chat : entityList ) {
            list.add( toDto( chat ) );
        }

        return list;
    }
}
