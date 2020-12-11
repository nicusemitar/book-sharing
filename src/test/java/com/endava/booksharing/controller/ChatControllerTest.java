package com.endava.booksharing.controller;

import com.endava.booksharing.model.ChatMessage;
import com.endava.booksharing.model.enums.MessageType;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import com.endava.booksharing.utils.TestAnnotationMethodHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.endava.booksharing.utils.ChatMessageUtils.SAMPLE_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Mock
    private TestAnnotationMethodHandler annotationMethodHandler;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private ChatController controller;

    @BeforeEach
    public void setup() {
        this.annotationMethodHandler.registerHandler(controller);
        this.annotationMethodHandler.setDestinationPrefixes(Arrays.asList("/app"));
        this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
        this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
        this.annotationMethodHandler.afterPropertiesSet();
    }

    @Test
    void shouldSendMessageToWebSocket() throws Exception {
        byte[] payload = new ObjectMapper().writeValueAsBytes(SAMPLE_MESSAGE);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.CONNECT);
        headerAccessor.setDestination("/app/chat.sendMessage");
        headerAccessor.setSessionId("0");
        headerAccessor.setUser(() -> "username");
        headerAccessor.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(payload).setHeaders(headerAccessor).build();

        this.annotationMethodHandler.handleMessage(message);

        ChatMessage actual = this.controller.sendMessage(SAMPLE_MESSAGE);

        assertAll(
                () -> assertEquals(SAMPLE_MESSAGE.getSender(), actual.getSender()),
                () -> assertEquals(SAMPLE_MESSAGE.getContent(), actual.getContent()),
                () -> assertEquals(SAMPLE_MESSAGE.getType(), actual.getType())
        );
    }

    @Test
    void shouldAddUserToWebSocket() throws Exception {

        byte[] payload = new ObjectMapper().writeValueAsBytes(SAMPLE_MESSAGE);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.CONNECT);
        headerAccessor.setDestination("/app/chat.addUser");
        headerAccessor.setSessionId("0");
        headerAccessor.setUser(() -> "username");
        headerAccessor.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(payload).setHeaders(headerAccessor).build();

        this.annotationMethodHandler.handleMessage(message);

        ChatMessage actual = this.controller.addUser(SAMPLE_MESSAGE, headerAccessor);

        assertAll(
                () -> assertEquals(SAMPLE_MESSAGE.getSender(), actual.getSender()),
                () -> assertEquals(SAMPLE_MESSAGE.getContent(), actual.getContent()),
                () -> assertEquals(SAMPLE_MESSAGE.getType(), actual.getType())
        );
    }

}
