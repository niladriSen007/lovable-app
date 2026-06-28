package com.niladri.lovable_app.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String stripePriceId;

    @Column(nullable = false)
    Integer maxProjects;

    @Column(nullable = false)
    Integer maxTokensPerDay;

    @Column(nullable = false)
    Integer maxPreviews;

    @Column(nullable = false)
    Boolean unlimitedAi;

    @Column(nullable = false)
    Boolean isActive = true;

}
