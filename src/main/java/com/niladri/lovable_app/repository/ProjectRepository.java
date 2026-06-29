package com.niladri.lovable_app.repository;

import com.niladri.lovable_app.dto.response.project.ProjectSummaryResponse;
import com.niladri.lovable_app.entity.Project;
import com.niladri.lovable_app.enums.ProjectRole;
import com.niladri.lovable_app.projection.ProjectWithRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
//    Optional<Project> findByOwnerId(Long userId);

    @Query(
            value =
                    """
                            SELECT p as project, pm.projectRole as role
                            FROM Project p INNER JOIN ProjectMember pm
                            ON p.id = pm.project.id
                            WHERE p.deletedAt IS NULL
                            AND pm.user.id = :userId
                            ORDER BY p.updatedAt DESC
                            """
    )
    Page<ProjectWithRole> findAllProjectsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(
            """
                        SELECT p FROM Project p 
                                WHERE p.deletedAt IS NULL 
                                AND p.id = :projectId
                                AND EXISTS (
                                SELECT 1 FROM ProjectMember pm
                                WHERE pm.project.id = p.id
                                AND pm.user.id = :userId
                                )
                    """
    )
    Optional<Project> findAccessibleProjectsByUserId(@Param("userId") Long userId, @Param("projectId") Long projectId);



    @Query("""
                        SELECT p as project, pm.projectRole as role
                        FROM Project p INNER JOIN ProjectMember pm
                        ON p.id = pm.project.id
                        WHERE p.deletedAt IS NULL
                        AND p.id = :projectId
                        AND pm.user.id = :userId
            """)
    Optional<ProjectWithRole> findAccessibleProjectByUserIdWithRole(@Param("projectId") Long projectId,
                                                                @Param("userId") Long userId);


}
