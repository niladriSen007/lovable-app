package com.niladri.lovable_app.projection;

import com.niladri.lovable_app.entity.Project;
import com.niladri.lovable_app.enums.ProjectRole;

public interface ProjectWithRole {
    Project getProject();

    ProjectRole getRole();
}
