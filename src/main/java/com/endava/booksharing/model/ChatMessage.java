package com.endava.booksharing.model;

import com.endava.booksharing.model.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}