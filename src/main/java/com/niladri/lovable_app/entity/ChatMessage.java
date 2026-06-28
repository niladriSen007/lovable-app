package com.niladri.lovable_app.entity;

import com.niladri.lovable_app.enums.MessageRole;

import java.time.Instant;

public class ChatMessage {
    Long id;
    ChatSession chatSession;
    String content;
    MessageRole messageRole;
    String toolCalls; // Json array of tools which are called
    Integer tokensUsed;
    Instant createdAt;
}
