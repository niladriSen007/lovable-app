package com.niladri.lovable_app.entity;

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
@Table(name = "usage_logs")
public class UsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    String action;

    Integer tokensUsed;
    Integer durationMs;

    @Column(columnDefinition = "TEXT")
    String metadata;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;
}
