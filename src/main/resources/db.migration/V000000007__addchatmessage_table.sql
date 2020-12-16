CREATE SEQUENCE
seq_chat_message
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

CREATE TABLE t_chat_message
(
    id BIGINT DEFAULT nextval('seq_chat_message') PRIMARY KEY,
    message_type VARCHAR,
    message_sender VARCHAR NOT NULL,
    message_content VARCHAR (2048) NOT NULL
);
