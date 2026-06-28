package com.niladri.lovable_app.entity;

import com.niladri.lovable_app.enums.ProjectPreviewStatus;
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
@Table(name = "project_previews")
public class ProjectPreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    Project project;

    String namespace;
    String podName;
    String previewUrl;

    @Enumerated(EnumType.STRING)
    ProjectPreviewStatus status;

    Instant startedAt;
    Instant terminatedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;
}
