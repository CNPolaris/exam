package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.utils.RespBean;

import java.util.List;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
public interface IRolePermissionService extends IService<RolePermission> {
    /**
     * 获取全部角色权限关联表
     * @param page 分页
     * @return RespBean
     */
    RespBean getAllRolePermissionList(Page<RolePermission> page);
    /**
     * 根据参数获取全部角色权限关联表
     * @param page 分页
     * @param param 参数
     * @return RespBean
     */
    RespBean getAllRolePermissionList(Page<RolePermission>page, RolePermission param);

    /**
     * 添加权限角色关联
     * @param param 参数
     * @return RespBean
     */
    RespBean create(RolePermission param);

    /**
     * 删除权限角色关联
     * @param id id
     * @return RespBean
     */
    RespBean delete(Integer id);

    /**
     * 更新权限角色关联
     * @param param 参数
     * @return RespBean
     */
    RespBean update(RolePermission param);

    /**
     * 查询权限角色关联
     * @param roleId 角色id
     * @param page 分页
     * @return RespBean
     */
    RespBean search(Page<RolePermission>page,Integer roleId);

    /**
     * 资源分配
     * @param roleId Integer
     * @param permissionIds List<Integer>
     */
    void allocPermission(Integer roleId, List<Integer> permissionIds);

    /**
     * 根据角色id获取权限ids
     * @param roleId Integer
     * @return List<Integer>
     */
    List<Integer> selectPermissionIdByRoleId(Integer roleId);
}
