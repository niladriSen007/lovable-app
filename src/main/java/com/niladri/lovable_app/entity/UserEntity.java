package com.niladri.lovable_app.entity;

import jakarta.persistence.*;
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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(unique = true, nullable = true)
    String email;
    @Column(nullable = false)
    String password;
    String avatarUrl;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    Instant updatedAt;
    Instant deletedAt;

}
