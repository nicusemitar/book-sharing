package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.ChatMessageDto;
import com.endava.booksharing.model.ChatMessage;
import com.endava.booksharing.model.enums.MessageType;
import lombok.NoArgsConstructor;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ChatMessageMapper {

    public static final Function<ChatMessage, ChatMessageDto> mapChatMessageToChatMessageDto =
            chatMessage -> ChatMessageDto.builder()
                    .content(chatMessage.getContent())
                    .sender(chatMessage.getSender())
                    .type(chatMessage.getType().toString())
                    .build();

    public static final Function<ChatMessageDto, ChatMessage> mapChatMessageDtoToChatMessage =
            chatMessageDto -> ChatMessage.builder()
                    .content(chatMessageDto.getContent())
                    .sender(chatMessageDto.getSender())
                    .type(MessageType.valueOf(chatMessageDto.getType()))
                    .build();
}
