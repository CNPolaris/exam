package com.polaris.exam.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Role;
import com.polaris.exam.service.IRoleService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Api(value = "角色管理模块",tags="RoleController")
@RestController
@RequestMapping("/api/admin/role")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取角色列表")
    @PostMapping("/list")
    public RespBean getAllRoleList(Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestBody(required = false) Role param){
        Page<Role> rolePage = new Page<>(page,limit);
        JSONObject jsonObject = JSONUtil.parseObj(param);
        if(jsonObject.isEmpty()){
            return roleService.getAllRoleList(rolePage);
        }
        return roleService.getAllRoleList(rolePage,param);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/create")
    public RespBean create(@RequestBody Role param){
        if(JSONUtil.parseObj(param).isEmpty()){
            return RespBean.error("参数不能为空");
        }
        Role role = roleService.create(param);
        if(role!=null){
            return RespBean.success("成功",role);
        }else {
            return RespBean.error("创建失败");
        }
    }

    @ApiOperation(value = "删除角色")
    @GetMapping("/delete/{id}")
    public RespBean delete(@PathVariable Integer id){
        if(roleService.deleteById(id)!=0){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "更新角色信息")
    @PostMapping("/update")
    public RespBean update(@RequestBody Role param){
        if(JSONUtil.parseObj(param).isEmpty()){
            return RespBean.error("参数不能为空");
        }
        Role role = roleService.update(param);
        if(role!=null){
            return RespBean.success("更新成功",role);
        }
        return RespBean.error("角色不存在");
    }

    @ApiOperation(value = "更新角色有效状态")
    @GetMapping("/status/{id}")
    public RespBean updateRoleStatus(@PathVariable Integer id, @RequestParam Integer status){
        Role role = roleService.updateRoleStatus(id, status);
        return RespBean.success("成功",role);
    }
}
