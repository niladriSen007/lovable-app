package com.niladri.lovable_app.repository;

import com.niladri.lovable_app.entity.ProjectMember;
import com.niladri.lovable_app.entity.ProjectMemberId;
import com.niladri.lovable_app.enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    @Query("""
            
                SELECT pm.user.id from ProjectMember pm 
                            where pm.project.id = :projectId
            """)
    List<Long> findMembersByProjectId(@Param("projectId") Long projectId);

    List<ProjectMember> findByProjectMemberIdProjectId(Long projectId);

    @Query("""
                    SELECT pm.projectRole FROM ProjectMember pm
                    WHERE pm.project.id = :projectId AND pm.user.id = :userId
            """)
    Optional<ProjectRole> findLoggedInUserRoleByProjectIdAndUserId(@Param("projectId") Long projectId, @Param("userId") Long userId);
}
