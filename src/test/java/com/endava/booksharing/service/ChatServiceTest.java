package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.ChatMessageDto;
import com.endava.booksharing.repository.ChatMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

import static com.endava.booksharing.utils.ChatMessageUtils.SAMPLE_MESSAGE;
import static com.endava.booksharing.utils.ChatMessageUtils.SAMPLE_MESSAGE_DTO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    void shouldSaveMessage(){
        List<ChatMessageDto> actualChatMessage = Collections.singletonList(SAMPLE_MESSAGE_DTO);
        when(chatMessageRepository.save(SAMPLE_MESSAGE)).thenReturn(SAMPLE_MESSAGE);

        List<ChatMessageDto> expectedChatMessage = chatService.saveMessage(SAMPLE_MESSAGE_DTO);

        assertAll(
                () -> assertEquals(actualChatMessage.get(0).getContent(), expectedChatMessage.get(0).getContent()),
                () -> assertEquals(actualChatMessage.get(0).getSender(), expectedChatMessage.get(0).getSender()),
                () -> assertEquals(actualChatMessage.get(0).getType(), expectedChatMessage.get(0).getType())
        );

        verify(chatMessageRepository).save(SAMPLE_MESSAGE);
    }

    @Test
    void shouldAddUser(){
        when(chatMessageRepository.findAll(ArgumentMatchers.any(Sort.class))).thenReturn(Collections.singletonList(SAMPLE_MESSAGE));
        List<ChatMessageDto> actualChatMessage = Collections.singletonList(SAMPLE_MESSAGE_DTO);

        List<ChatMessageDto> expectedChatMessage = chatService.addUserToChat(SAMPLE_MESSAGE_DTO);

        assertAll(
                () -> assertEquals(actualChatMessage.get(0).getContent(), expectedChatMessage.get(0).getContent()),
                () -> assertEquals(actualChatMessage.get(0).getSender(), expectedChatMessage.get(0).getSender()),
                () -> assertEquals(actualChatMessage.get(0).getType(), expectedChatMessage.get(0).getType())
        );

        verify(chatMessageRepository).findAll(ArgumentMatchers.any(Sort.class));
    }
}
