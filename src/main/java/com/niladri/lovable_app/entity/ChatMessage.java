package com.niladri.lovable_app.entity;

import com.niladri.lovable_app.enums.MessageRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "chat_session_id", nullable = false)
    ChatSession chatSession;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MessageRole messageRole;

    @Column(columnDefinition = "TEXT")
    String toolCalls;

    Integer tokensUsed;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;
}
