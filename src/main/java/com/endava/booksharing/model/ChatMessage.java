package com.endava.booksharing.model;

import com.endava.booksharing.model.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "t_chat_message")
public class ChatMessage {

    @Id
    @SequenceGenerator(name = "chat_message_id_generator", sequenceName = "seq_chat_message", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_id_generator")
    private Long id;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "message_content")
    private String content;

    @Column(name = "message_sender")
    private String sender;
}