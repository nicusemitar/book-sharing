package com.endava.booksharing.utils;

import com.endava.booksharing.model.ChatMessage;
import com.endava.booksharing.model.enums.MessageType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageUtils {
    public static final ChatMessage SAMPLE_MESSAGE = ChatMessage.builder()
            .content("hello")
            .sender("username")
            .type(MessageType.CHAT).build();

    public static final Map<String, Object> sessionAttributes = new HashMap<>();
    public static final Map<String, String> currentUserAttributes = new HashMap<>();
}
