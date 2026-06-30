package com.niladri.lovable_app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.niladri.lovable_app.enums.ProjectPermissions.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    EDITOR(VIEW,EDIT,DELETE,VIEW_MEMBERS),
    VIEWER(VIEW,VIEW_MEMBERS),
    OWNER(VIEW,EDIT,DELETE,MANAGE_MEMBERS,VIEW_MEMBERS);

    private final Set<ProjectPermissions> permissions;

    ProjectRole(ProjectPermissions... permissions){
        this.permissions = Set.of(permissions);
    }

}
