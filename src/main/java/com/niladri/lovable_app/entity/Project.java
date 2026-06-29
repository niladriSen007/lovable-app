package com.niladri.lovable_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "projects", indexes = {
        @Index(name = "idx_projects_updated_at_desc", columnList = "updated_at DESC,deleted_at")
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = "Project name must be provided")
    String name;

    @Column(nullable = false)
    @NotBlank(message = "Project description must be provided")
    String description;

//    @ManyToOne
//    @JoinColumn(name = "owner_id", nullable = false)
//    UserEntity owner;

//    @ManyToOne
//    @JoinColumn(name = "member_id", nullable = false)
//    ProjectMember member;

    @Builder.Default
    Boolean isPublic = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    Instant updatedAt;

    Instant deletedAt;
}
