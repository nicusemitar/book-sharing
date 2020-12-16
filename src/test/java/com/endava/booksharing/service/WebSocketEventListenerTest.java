package com.endava.booksharing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Collections;
import java.util.HashMap;

import static com.endava.booksharing.TestConstants.DEFAULT_USERNAME;
import static com.endava.booksharing.TestConstants.SESSION;
import static com.endava.booksharing.utils.ChatMessageUtils.SAMPLE_MESSAGE;
import static com.endava.booksharing.utils.ChatMessageUtils.currentUserAttributes;
import static com.endava.booksharing.utils.ChatMessageUtils.sessionAttributes;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class WebSocketEventListenerTest {

    @Mock
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Mock
    private StompHeaderAccessor stompHeaderAccessor;

    @InjectMocks
    private WebSocketEventListener webSocketEventListener;

    @Test
    void shouldHandleWebSocketConnectListener() throws Exception {
        byte[] payload = new ObjectMapper().writeValueAsBytes(SAMPLE_MESSAGE);
        SessionConnectedEvent event = new SessionConnectedEvent(SAMPLE_MESSAGE, new Message<byte[]>() {
            @Override
            public byte[] getPayload() {
                return payload;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        }, () -> "username");

        this.webSocketEventListener.handleWebSocketConnectListener(event);
    }

    @Test
    void shouldTrowNullPointerOnHandleWebSocketDisconnectListener() throws Exception {
        byte[] payload = new ObjectMapper().writeValueAsBytes(SAMPLE_MESSAGE);

        SessionDisconnectEvent event = new SessionDisconnectEvent(SAMPLE_MESSAGE, new Message<byte[]>() {
            @Override
            public byte[] getPayload() {
                return payload;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        }, "1", CloseStatus.NORMAL);

        assertThrows(NullPointerException.class, () -> webSocketEventListener.handleWebSocketDisconnectListener(event));
    }

    @Test
    void shouldHandleWebSocketDisconnectListener() throws Exception {
        byte[] payload = new ObjectMapper().writeValueAsBytes(Collections.singletonList(SAMPLE_MESSAGE));
        currentUserAttributes.put(DEFAULT_USERNAME, DEFAULT_USERNAME);
        sessionAttributes.put(SESSION, currentUserAttributes);

        SessionDisconnectEvent event = new SessionDisconnectEvent(SAMPLE_MESSAGE, new Message<byte[]>() {
            @Override
            public byte[] getPayload() {
                return payload;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(sessionAttributes);
            }
        }, "1", CloseStatus.NORMAL);

        webSocketEventListener.handleWebSocketDisconnectListener(event);
    }
}
