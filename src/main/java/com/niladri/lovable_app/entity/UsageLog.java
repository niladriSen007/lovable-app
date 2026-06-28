package com.niladri.lovable_app.entity;

import java.time.Instant;

public class UsageLog {
    Long id;
    UserEntity user;
    Project project;

    String action;

    Integer tokensUsed;
    Integer durationMs;

    String metadata;

    Instant createdAt;

}
