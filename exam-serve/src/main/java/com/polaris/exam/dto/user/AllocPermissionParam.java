package com.polaris.exam.dto.user;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class AllocPermissionParam {
    private Integer roleId;
    private List<Integer> permissionIds;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
