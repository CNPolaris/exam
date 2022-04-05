package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.utils.RespBean;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据roleId查询roleName
     * @param roleId id
     * @return  String
     */
    String getRoleNameById(Integer roleId);
    /**
     * 获取全部角色表
     * @param page 分页
     * @return RespBean
     */
    RespBean getAllRoleList(Page<Role> page);
    /**
     * 获取全部角色表
     * @param page 分页
     * @param param 参数
     * @return RespBean
     */
    RespBean getAllRoleList(Page<Role> page,Role param);

    /**
     * 添加角色
     * @param param  Role
     * @return Role
     */
    Role create(Role param);

    /**
     * 通过id删除角色
     * @param id Integer
     * @return int
     */
    int deleteById(Integer id);

    /**
     * 更新角色信息
     * @param param Role
     * @return Role
     */
    Role update(Role param);

    /**
     * 增加用户数量
     * @param code Integer
     */
    void increaseCount(Integer code);

    /**
     * 减少用户数量
     * @param code Integer
     */
    void decreaseCount(Integer code);

    /**
     * 更新角色有效状态
     * @param id Integer
     * @param status Integer
     * @return Role
     */
    Role updateRoleStatus(Integer id,Integer status);
}
