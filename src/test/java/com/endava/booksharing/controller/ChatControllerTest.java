package com.endava.booksharing.controller;

import com.endava.booksharing.api.dto.ChatMessageDto;
import com.endava.booksharing.service.ChatService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import com.endava.booksharing.utils.TestAnnotationMethodHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.endava.booksharing.utils.ChatMessageUtils.SAMPLE_MESSAGE;
import static com.endava.booksharing.utils.ChatMessageUtils.SAMPLE_MESSAGE_DTO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Mock
    private TestAnnotationMethodHandler annotationMethodHandler;

    @MockBean
    private ChatService chatService;

    @InjectMocks
    @Autowired
    private ChatController controller;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setup() {
        this.annotationMethodHandler.registerHandler(controller);
        this.annotationMethodHandler.setDestinationPrefixes(Collections.singletonList("/app"));
        this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
        this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
        this.annotationMethodHandler.afterPropertiesSet();
    }

    @Test
    void shouldSendMessageToWebSocket() throws Exception {
        when(chatService.saveMessage(SAMPLE_MESSAGE_DTO)).thenReturn(Collections.singletonList(SAMPLE_MESSAGE_DTO));

        byte[] payload = new ObjectMapper().writeValueAsBytes(Collections.singletonList(SAMPLE_MESSAGE));

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.CONNECT);
        headerAccessor.setDestination("/app/chat.sendMessage");
        headerAccessor.setSessionId("0");
        headerAccessor.setUser(() -> "username");
        headerAccessor.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(payload).setHeaders(headerAccessor).build();

        this.annotationMethodHandler.handleMessage(message);

        List<ChatMessageDto> actual = this.controller.sendMessage(SAMPLE_MESSAGE_DTO);

        assertAll(
                () -> assertEquals(SAMPLE_MESSAGE.getSender(), actual.get(0).getSender()),
                () -> assertEquals(SAMPLE_MESSAGE.getContent(), actual.get(0).getContent()),
                () -> assertEquals(SAMPLE_MESSAGE.getType().toString(), actual.get(0).getType())
        );

        verify(chatService).saveMessage(SAMPLE_MESSAGE_DTO);
    }

    @Test
    void shouldAddUserToWebSocket() throws Exception {
        when(chatService.addUserToChat(SAMPLE_MESSAGE_DTO)).thenReturn(Collections.singletonList(SAMPLE_MESSAGE_DTO));

        byte[] payload = new ObjectMapper().writeValueAsBytes(SAMPLE_MESSAGE);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.CONNECT);
        headerAccessor.setDestination("/app/chat.addUser");
        headerAccessor.setSessionId("0");
        headerAccessor.setUser(() -> "username");
        headerAccessor.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(payload).setHeaders(headerAccessor).build();

        this.annotationMethodHandler.handleMessage(message);

        List<ChatMessageDto> actual = this.controller.addUser(SAMPLE_MESSAGE_DTO, headerAccessor);

        assertAll(
                () -> assertEquals(SAMPLE_MESSAGE.getSender(), actual.get(0).getSender()),
                () -> assertEquals(SAMPLE_MESSAGE.getContent(), actual.get(0).getContent()),
                () -> assertEquals(SAMPLE_MESSAGE.getType().toString(), actual.get(0).getType())
        );

        verify(chatService).addUserToChat(SAMPLE_MESSAGE_DTO);
    }

}
