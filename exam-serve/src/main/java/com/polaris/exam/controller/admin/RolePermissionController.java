package com.polaris.exam.controller.admin;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.pojo.RolePermission;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IPermissionService;
import com.polaris.exam.service.IRolePermissionService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Api(value = "角色权限关联管理模块",tags="RolePermissionController")
@RestController("AdminRPController")
@RequestMapping("/api/admin/rp")
public class RolePermissionController {
    private final IRolePermissionService rolePermissionService;
    private final IUserService userService;
    private final IPermissionService permissionService;
    public RolePermissionController(IRolePermissionService rolePermissionService, IUserService userService, IPermissionService permissionService) {
        this.rolePermissionService = rolePermissionService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "获取角色权限表")
    @GetMapping("/list")
    public RespBean getAllRolePermissionList(Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestBody(required = false) RolePermission param){
        Page<RolePermission> objectPage = new Page<>(page, limit);
        JSONObject jsonObject = JSONUtil.parseObj(param);
        if(jsonObject.isEmpty()){
            return rolePermissionService.getAllRolePermissionList(objectPage);
        }
        return rolePermissionService.getAllRolePermissionList(objectPage,param);
    }
    @ApiOperation(value = "添加权限角色关联")
    @PostMapping("/create")
    public RespBean create(@RequestBody RolePermission param){
        return rolePermissionService.create(param);
    }

    @ApiOperation(value = "删除权限角色关联")
    @GetMapping("/delete")
    public RespBean delete(@RequestParam Integer id){
        return rolePermissionService.delete(id);
    }

    @ApiOperation(value = "更新权限角色关联")
    @PostMapping("/update")
    public RespBean update(@RequestBody RolePermission param){
        return rolePermissionService.update(param);
    }

    @ApiOperation(value = "查询权限角色关联")
    @GetMapping("/search")
    public RespBean search(@RequestParam Integer page,@RequestParam Integer limit,@RequestParam Integer roleId){
        Page<RolePermission> objectPage = new Page<>(page, limit);
        return rolePermissionService.search(objectPage,roleId);
    }

    @ApiOperation(value = "用户全部权限")
    @GetMapping("/listAll")
    public RespBean userAllPermission(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<Integer> permissionIds = rolePermissionService.selectPermissionIdByRoleId(user.getRoleId());
        List<Permission> permissionList = permissionService.selectPermissionByIds(permissionIds);
        return RespBean.success("成功",permissionList);
    }
}
