package com.niladri.lovable_app.repository;

import com.niladri.lovable_app.dto.response.project.ProjectSummaryResponse;
import com.niladri.lovable_app.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByOwnerId(Long userId);

    @Query(
            value =
                    """
                            SELECT p FROM Project p 
                            WHERE p.deletedAt IS NULL 
                            AND p.owner.id = :userId
                            ORDER BY p.updatedAt DESC
                            """,
            countQuery =
                    """
                            SELECT COUNT(p) FROM Project p 
                            WHERE p.deletedAt IS NULL 
                            AND p.owner.id = :userId
                            """
    )
    Page<Project> findAllProjectsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(
            """
                        SELECT p FROM Project p 
                                            LEFT JOIN FETCH p.owner
                                WHERE p.deletedAt IS NULL 
                                AND p.id = :projectId  
                                                AND p.owner.id = :ownerId
                    """
    )
    Optional<Project> findAccessibleProjectsByOwnerId(@Param("ownerId") Long ownerId, @Param("projectId") Long projectId);

}
