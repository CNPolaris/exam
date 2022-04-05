package com.polaris.exam.service;

import com.polaris.exam.pojo.*;

import java.util.List;

/**
 * @author cnpolaris
 * @version 1.0
 */
public interface AdminService {
    /**
     * 获取全部用户表
     * @return List
     */
    List<User> getAllUserList();

    /**
     * 获取全部角色表
     * @return List
     */
    List<Role> getAllRoleList();

    /**
     * 获取全部权限表
     * @return List
     */
    List<Permission> getAllPermissionList();

    /**
     * 获取全部角色权限关联表
     * @return List
     */
    List<RolePermission> getAllRolePermissionList();

    /**
     * 获取全部学科表
     * @return List
     */
    List<Subject> getAllSubjectList();
}
