package com.niladri.lovable_app.entity;

import com.niladri.lovable_app.enums.ProjectRole;

import java.time.Instant;

public class ProjectMember {
    ProjectMemberId projectMemberId;
    Project project;
    UserEntity user;
    ProjectRole  projectRole;
    Instant invitedAt;
    Instant joinedAt;
    UserEntity invitedBy;
}
