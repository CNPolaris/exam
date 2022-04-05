package com.polaris.exam.service.impl;

import com.polaris.exam.pojo.*;
import com.polaris.exam.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {

    /**
     * 获取全部用户表
     *
     * @return List
     */
    @Override
    public List<User> getAllUserList() {
        return null;
    }

    /**
     * 获取全部角色表
     *
     * @return List
     */
    @Override
    public List<Role> getAllRoleList() {
        return null;
    }

    /**
     * 获取全部权限表
     *
     * @return List
     */
    @Override
    public List<Permission> getAllPermissionList() {
        return null;
    }


    /**
     * 获取全部角色权限关联表
     *
     * @return List
     */
    @Override
    public List<RolePermission> getAllRolePermissionList() {
        return null;
    }

    /**
     * 获取全部学科表
     *
     * @return List
     */
    @Override
    public List<Subject> getAllSubjectList() {
        return null;
    }
}
