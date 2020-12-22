package com.endava.booksharing.service;


import com.endava.booksharing.api.dto.ChatMessageDto;
import com.endava.booksharing.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection from user [{}]", event.getUser().getName());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = headerAccessor.getSessionAttributes().get("username").toString();
        if(username != null) {
            log.info("User Disconnected : " + username);

            ChatMessageDto chatMessage = ChatMessageDto.builder()
                    .type("LEAVE")
                    .sender(username)
                    .build();

            messagingTemplate.convertAndSend("/topic/public", Collections.singletonList(chatMessage));
        }
    }
}
