package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.utils.RespBean;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 获取全部的权限列表
     * @return List
     */
    List<Permission> queryAll();
    /**
     * 获取全部权限表
     * @param page 分页
     * @return RespBean
     */
    RespBean getAllPermissionList(Page<Permission> page);

    /**
     * 根据参数获取全部权限表
     * @param page 分页
     * @param param 参数
     * @return RespBean
     */
    RespBean getAllPermissionList(Page<Permission> page, Permission param);

    /**
     * 添加权限
     * @param param 参数
     * @return RespBean
     */
    RespBean create(Permission param);

    /**
     * 删除权限
     * @param permissionId  id
     * @return RespBean
     */
    RespBean delete(Integer permissionId);

    /**
     * 更新权限
     * @param param 参数
     * @return RespBean
     */
    RespBean update(Permission param);

    /**
     * 查询权限
     * @param id id
     * @param name 名称
     * @param url 资源定位符
     * @return RespBean
     */
    RespBean search(Integer id,String name,String url);

    /**
     * 更新权限有效状态
     * @param id Integer
     * @param status Integer
     * @return Permission
     */
    Permission updatePermissionStatus(Integer id, Integer status);
    /**
     * 根据ids获取权限列表
     * @param ids List<Integer>
     * @return List<Permission>
     */
    List<Permission> selectPermissionByIds(List<Integer> ids);

    /**
     * 根据目录id获取所属权限
     * @param categoryId Integer
     * @return List<Permission>
     */
    List<Permission> selectPermissionByCategory(Integer categoryId);
}
