package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.RolePermission;
import com.polaris.exam.mapper.RolePermissionMapper;
import com.polaris.exam.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.RespBean;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    private final RolePermissionMapper rolePermissionMapper;

    public RolePermissionServiceImpl(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * 获取全部角色权限关联表
     *
     * @param page 分页
     * @return RespBean
     */
    @Override
    public RespBean getAllRolePermissionList(Page<RolePermission> page) {
        Map<String,Object> returnMap = new HashMap<>();
        IPage<RolePermission> data = rolePermissionMapper.selectPage(page, null);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 根据参数获取全部角色权限关联表
     *
     * @param page  分页
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean getAllRolePermissionList(Page<RolePermission> page, RolePermission param) {
        HashMap<String, Object> returnMap = new HashMap<>();
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        IPage<RolePermission> data = rolePermissionMapper.selectPage(page, queryWrapper);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 添加权限角色关联
     *
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean create(RolePermission param) {
        try{
            RolePermission rolePermission = new RolePermission();
            BeanUtil.copyProperties(param,rolePermission);
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("permission_id",param.getPermissionId());
            if(rolePermissionMapper.selectCount(queryWrapper)>0){
                return RespBean.error("该关联已经存在");
            }
            rolePermissionMapper.insert(rolePermission);
            return RespBean.success("添加权限角色关联成功",rolePermission);
        }catch (Exception e) {
            return RespBean.error("添加权限角色关联失败", e);
        }
    }

    /**
     * 删除权限角色关联
     *
     * @param id id
     * @return RespBean
     */
    @Override
    public RespBean delete(Integer id) {
        try{
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            rolePermissionMapper.delete(queryWrapper);
            return RespBean.success("删除权限角色关联成功");
        }catch (Exception e){
            return RespBean.error("删除权限角色关联失败");
        }
    }

    /**
     * 更新权限角色关联
     *
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean update(RolePermission param) {
        try{
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",param.getId());
            RolePermission rolePermission = rolePermissionMapper.selectOne(queryWrapper);
            if(rolePermission==null){
                return RespBean.error("不存在该关联");
            }
            rolePermission.setRoleId(param.getRoleId());
            rolePermission.setPermissionId(param.getPermissionId());
            rolePermissionMapper.updateById(rolePermission);
            return RespBean.success("关联更新成功",rolePermission);
        }catch (Exception e) {
            return RespBean.error("更新关联失败");
        }
    }

    /**
     * 查询权限角色关联
     *
     * @param roleId 角色id
     * @return RespBean
     */
    @Override
    public RespBean search(Page<RolePermission>page,Integer roleId) {
        try{
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleId);
            HashMap<String, Object> returnMap = new HashMap<>();
            IPage<RolePermission> data = rolePermissionMapper.selectPage(page, queryWrapper);
            returnMap.put("total",data.getTotal());
            returnMap.put("data",data.getRecords());
            return RespBean.success("查询成功",returnMap);
        }catch (Exception e){
            return RespBean.error("查询失败");
        }
    }

    /**
     * 资源分配
     *
     * @param roleId        Integer
     * @param permissionIds List<Integer>
     */
    @Override
    public void allocPermission(Integer roleId, List<Integer> permissionIds) {
        permissionIds.forEach(permissionId->{
            if(rolePermissionMapper.selectCount(new QueryWrapper<RolePermission>().eq("role_id", roleId).eq("permission_id",permissionId))==0){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermission.setCreateTime(new Date());
                rolePermission.setStatus(StatusEnum.OK.getCode());
                saveOrUpdate(rolePermission);
            }
        });
    }

    /**
     * 根据角色id获取权限ids
     *
     * @param roleId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> selectPermissionIdByRoleId(Integer roleId) {
        List<Integer> permissionIds = new ArrayList<>();
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", roleId).eq("status", StatusEnum.OK.getCode()));
        rolePermissionList.forEach(r->{
            permissionIds.add(r.getPermissionId());
        });
        return permissionIds;
    }
}
