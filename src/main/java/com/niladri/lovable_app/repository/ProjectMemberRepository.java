package com.niladri.lovable_app.repository;

import com.niladri.lovable_app.entity.ProjectMember;
import com.niladri.lovable_app.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    @Query("""
            
                SELECT pm.user.id from ProjectMember pm 
                            where pm.project.id = :projectId
            """)
    List<Long> findMembersByProjectId(@Param("projectId") Long projectId);

    List<ProjectMember> findByProjectMemberIdProjectId(Long projectId);
}
