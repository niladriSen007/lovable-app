package com.niladri.lovable_app.entity;

import java.time.Instant;

public class ChatSession {
    Project project;
    UserEntity user;

    String title;

    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;
}
