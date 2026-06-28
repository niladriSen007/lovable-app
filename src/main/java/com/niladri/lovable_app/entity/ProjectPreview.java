package com.niladri.lovable_app.entity;


import com.niladri.lovable_app.enums.ProjectPreviewStatus;

import java.time.Instant;

public class ProjectPreview {
    Long id;
    Project project;

    String namespace;
    String podName;
    String previewUrl;

    ProjectPreviewStatus status;

    Instant startedAt;
    Instant terminatedAt;

    Instant createdAt;
}
