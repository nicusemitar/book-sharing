package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.ChatMessageDto;
import com.endava.booksharing.model.ChatMessage;
import com.endava.booksharing.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.endava.booksharing.utils.mappers.ChatMessageMapper.mapChatMessageDtoToChatMessage;
import static com.endava.booksharing.utils.mappers.ChatMessageMapper.mapChatMessageToChatMessageDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessageDto> saveMessage(ChatMessageDto chatMessageRequestDto) {
        List<ChatMessageDto> chatMessageResponseDtoList = new ArrayList<>();
        chatMessageResponseDtoList.add(mapChatMessageToChatMessageDto.apply(chatMessageRepository.save(mapChatMessageDtoToChatMessage.apply(chatMessageRequestDto))));
        return chatMessageResponseDtoList;
    }

    public List<ChatMessageDto> addUserToChat(ChatMessageDto chatMessageRequestDto) {
        List<ChatMessageDto> chatMessages = chatMessageRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().limit(20)
                .sorted(Comparator.comparingLong(ChatMessage::getId)).map(mapChatMessageToChatMessageDto).collect(Collectors.toList());

        chatMessages.add(mapChatMessageToChatMessageDto.apply(mapChatMessageDtoToChatMessage.apply(chatMessageRequestDto)));
        return chatMessages;
    }
}
