package com.polaris.exam.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.service.IPermissionService;
import com.polaris.exam.service.IRolePermissionService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Api(value = "权限管理模块",tags = "PermissionController")
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    private final IPermissionService permissionService;
    private final IRolePermissionService rolePermissionService;

    public PermissionController(IPermissionService permissionService, IRolePermissionService rolePermissionService) {
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
    }

    @ApiOperation(value = "获取权限列表")
    @PostMapping("/list")
    public RespBean getAllPermissionList(Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestBody(required = false) Permission param){
        Page<Permission> permissionPage = new Page<>(page, limit);
        JSONObject jsonObject = JSONUtil.parseObj(param);
        if(jsonObject.isEmpty()){
            return permissionService.getAllPermissionList(permissionPage);
        }
        return permissionService.getAllPermissionList(permissionPage,param);
    }
    @ApiOperation(value = "添加权限")
    @PostMapping("/create")
    public RespBean create(Principal principal, @RequestBody Permission param){
        return permissionService.create(param);
    }
    @ApiOperation(value = "查询权限")
    @GetMapping("/search")
    public RespBean search(Principal principal,@RequestParam(required = false) Integer id,@RequestParam(required = false) String name,@RequestParam(required = false)String url){
        return permissionService.search(id,name,url);
    }
    @ApiOperation(value = "删除权限")
    @GetMapping("/delete/{id}")
    public RespBean delete(Principal principal,@PathVariable Integer id){
        return permissionService.delete(id);
    }
    @ApiOperation(value = "更新权限")
    @PostMapping("/update")
    public RespBean update(Principal principal,@RequestBody Permission param){
        return permissionService.update(param);
    }

    @ApiOperation(value = "获取全部权限")
    @GetMapping("/all")
    public RespBean allPermissionList(){
        return RespBean.success("成功",permissionService.queryAll());
    }

    @ApiOperation(value = "根据角色id获取权限列表")
    @GetMapping("/role")
    public RespBean selectPermissionByRoleId(@RequestParam Integer roleId){
        List<Integer> permissionIds = rolePermissionService.selectPermissionIdByRoleId(roleId);
        List<Permission> permissionList = permissionService.selectPermissionByIds(permissionIds);
        return RespBean.success("成功",permissionList);
    }

    @ApiOperation(value = "根据资源类别获取权限列表")
    @GetMapping("/category")
    public RespBean selectPermissionByCategory(@RequestParam Integer cateId){
        return RespBean.success("成功",permissionService.selectPermissionByCategory(cateId));
    }

    @ApiOperation(value = "更新权限有效状态")
    @GetMapping("/status/{id}")
    public RespBean updatePermissionStatus(@PathVariable Integer id,@RequestParam Integer status){
        Permission permission = permissionService.updatePermissionStatus(id, status);
        return RespBean.success("成功",permission);
    }

}
