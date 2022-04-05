package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.Role;
import com.polaris.exam.mapper.RoleMapper;
import com.polaris.exam.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.RespBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * 根据roleId查询roleName
     *
     * @param roleId id
     * @return String
     */
    @Override
    public String getRoleNameById(Integer roleId) {
        return roleMapper.selectOne(new QueryWrapper<Role>().eq("id",roleId)).getName();
    }

    /**
     * 获取全部角色表
     *
     * @param page 分页
     * @return RespBean
     */
    @Override
    public RespBean getAllRoleList(Page<Role> page) {
        HashMap<String, Object> returnMap = new HashMap<>();
        IPage<Role> data = roleMapper.selectPage(page, null);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);

    }

    /**
     * 获取全部角色表
     *
     * @param page  分页
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean getAllRoleList(Page<Role> page, Role param) {
        HashMap<String, Object> returnMap = new HashMap<>();
        Map<String, Object> beanToMap = BeanUtil.beanToMap(param, true, false);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(beanToMap,false);
        IPage<Role> data = roleMapper.selectPage(page,queryWrapper);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    @Override
    public Role create(Role param) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",param.getCode());
        Role role = roleMapper.selectOne(queryWrapper);
        if(role!=null){
            return role;
        }
        Date now = new Date();
        Role newRole = new Role();
        BeanUtil.copyProperties(param,newRole);
        newRole.setStatus(StatusEnum.OK.getCode());
        newRole.setCreateTime(now);
        newRole.setUpdateTime(now);
        newRole.setUserCount(0);
        roleMapper.insert(newRole);
        return newRole;
    }

    @Override
    public int deleteById(Integer id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public Role update(Role param) {
        Role role = roleMapper.selectById(param.getId());
        if(role==null){
            return null;
        }
        role.setUpdateTime(new Date());
        role.setName(param.getName());
        role.setDescription(param.getDescription());
        role.setStatus(param.getStatus());
        roleMapper.updateById(role);
        return role;
    }

    @Override
    public void increaseCount(Integer code) {
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("code", code));
        Integer userCount = role.getUserCount();
        role.setUserCount(userCount++);
        roleMapper.updateById(role);
    }

    @Override
    public void decreaseCount(Integer code) {
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("code", code));
        Integer userCount = role.getUserCount();
        role.setUserCount(userCount--);
        roleMapper.updateById(role);
    }

    /**
     * 更新角色有效状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Role updateRoleStatus(Integer id, Integer status) {
        Role role = getById(id);
        role.setStatus(status);
        role.setUpdateTime(new Date());
        save(role);
        return role;
    }
}
