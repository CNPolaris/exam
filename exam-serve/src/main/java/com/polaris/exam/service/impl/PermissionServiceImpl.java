package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.permission.PermissionResponse;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.mapper.PermissionMapper;
import com.polaris.exam.service.AdminCacheService;
import com.polaris.exam.service.IPermissionCategoryService;
import com.polaris.exam.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    private final PermissionMapper permissionMapper;
    private final IPermissionCategoryService categoryService;
    private final AdminCacheService adminCacheService;
    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper, IPermissionCategoryService categoryService, AdminCacheService adminCacheService) {
        this.permissionMapper = permissionMapper;
        this.categoryService = categoryService;
        this.adminCacheService = adminCacheService;
    }

    /**
     * 获取全部的权限列表
     * @return List
     */
    @Override
    public List<Permission> queryAll() {
        List<Permission> permissionAll = adminCacheService.getPermissionAll();
        if(permissionAll==null){
            permissionAll = permissionMapper.selectList(new QueryWrapper<Permission>());
            if(permissionAll!=null){
                adminCacheService.setPermissionAll(permissionAll);
            }
            return permissionAll;
        }
        return permissionAll;
    }

    /**
     * 获取全部权限表
     *
     * @param page 分页
     */
    @Override
    public RespBean getAllPermissionList(Page<Permission> page) {
        Map<String, Object> returnMap = new HashMap<>();
        IPage<Permission> data = permissionMapper.selectPage(page,null);
        returnMap.put("total",data.getTotal());
        ArrayList<PermissionResponse> permissionList = new ArrayList<>();
        data.getRecords().forEach(permission -> {
            PermissionResponse permissionResponse = BeanUtil.copyProperties(permission, PermissionResponse.class);
            permissionResponse.setCategoryName(categoryService.getById(permission.getCategoryId()).getName());
            permissionList.add(permissionResponse);
        });
        returnMap.put("data",permissionList);
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 根据参数获取全部权限表
     *
     * @param page  分页
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean getAllPermissionList(Page<Permission> page, Permission param) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> beanToMap = BeanUtil.beanToMap(param,true,false);
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(beanToMap,false);
        IPage<Permission> data = permissionMapper.selectPage(page,queryWrapper);
        returnMap.put("total", data.getTotal());

        ArrayList<PermissionResponse> permissionList = new ArrayList<>();
        data.getRecords().forEach(permission -> {
            PermissionResponse permissionResponse = BeanUtil.copyProperties(permission, PermissionResponse.class);
            permissionResponse.setCategoryName(categoryService.getById(permission.getCategoryId()).getName());
            permissionList.add(permissionResponse);
        });
        returnMap.put("data",permissionList);
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 添加权限
     *
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean create(Permission param) {
        try{
            Permission permission = new Permission();
            param.setCreateTime(new Date());
            BeanUtil.copyProperties(param,permission);
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("url",param.getUrl()).or().eq("name",param.getName());
            if(permissionMapper.selectCount(queryWrapper)>0){
                return RespBean.error("该权限已经存在");
            }
            permission.setStatus(StatusEnum.OK.getCode());
            permission.setCreateTime(new Date());
            save(permission);
            return RespBean.success("添加权限成功",permission);
        }catch (Exception e){
            return RespBean.error("添加权限失败",e);
        }
    }

    /**
     * 删除权限
     *
     * @param permissionId id
     * @return RespBean
     */
    @Override
    public RespBean delete(Integer permissionId) {
        try{
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",permissionId);
            if(permissionMapper.selectCount(queryWrapper).equals(0)){
                return RespBean.error("不存在该权限");
            }
            permissionMapper.deleteById(permissionId);
            return RespBean.success("删除权限成功");
        }catch (Exception e){
            return RespBean.error("删除权限失败",e);
        }
    }

    /**
     * 更新权限
     *
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean update(Permission param) {
        try{
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", param.getId());
            Permission permission = permissionMapper.selectOne(queryWrapper);
            if(permission==null){
                return RespBean.error("不存在权限,无法更新");
            }
            permission.setName(param.getName());
            permission.setDescription(param.getDescription());
            permission.setUrl(param.getUrl());
            permission.setStatus(param.getStatus());
            permission.setCategoryId(param.getCategoryId());
            permissionMapper.updateById(permission);
            return RespBean.success("更新权限成功",permission);
        }catch (Exception e){
            return RespBean.error("更新权限失败",e);
        }
    }

    /**
     * 查询权限
     *
     * @param id   id
     * @param name 名称
     * @param url  资源定位符
     * @return RespBean
     */
    @Override
    public RespBean search(Integer id, String name, String url) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).or().eq("name", name).or().eq("url", url);
        Permission permission = permissionMapper.selectOne(queryWrapper);
        return RespBean.success("查询权限成功",permission);
    }

    /**
     * 更新权限有效状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Permission updatePermissionStatus(Integer id, Integer status) {
        Permission permission = getById(id);
        permission.setStatus(status);
        update(permission);
        return permission;
    }

    /**
     * 根据ids获取权限列表
     *
     * @param ids List<Integer>
     * @return List<Permission>
     */
    @Override
    public List<Permission> selectPermissionByIds(List<Integer> ids) {
        return permissionMapper.selectBatchIds(ids);
    }

    @Override
    public List<Permission> selectPermissionByCategory(Integer categoryId) {
        return permissionMapper.selectList(new QueryWrapper<Permission>().eq("category_id",categoryId));
    }
}
