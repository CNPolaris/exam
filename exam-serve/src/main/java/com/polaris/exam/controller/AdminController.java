package com.polaris.exam.controller;

import com.polaris.exam.dto.user.AllocPermissionParam;
import com.polaris.exam.enums.UserTypeEnum;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IRolePermissionService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.service.MonitorService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "系统管理员模块",tags = "系统管理员模块")
@RestController
@RequestMapping("/api")
public class AdminController {
    private final IUserService userService;
    private final MonitorService monitorService;
    private final IRolePermissionService rolePermissionService;
    @Autowired
    public AdminController(IUserService userService, MonitorService monitorService, IRolePermissionService rolePermissionService) {
        this.userService = userService;
        this.monitorService = monitorService;
        this.rolePermissionService = rolePermissionService;
    }
    @ApiOperation(value = "禁用帐户")
    @GetMapping("/user/status/disable/{id}")
    public RespBean disableUser(@PathVariable Integer id){
        if(id==null){
            return RespBean.error("id不能为空");
        }
        User user = userService.disabledUserById(id);
        if(user!=null){
            return RespBean.success("帐户已禁用");
        }else {
            return RespBean.error("账户不存在");
        }
    }
    @ApiOperation(value = "恢复帐户")
    @GetMapping("/user/status/able/{id}")
    public RespBean ableUser(@PathVariable Integer id){
        if(id==null){
            return RespBean.error("id不能为空");
        }
        User user = userService.ableUserById(id);
        if(user!=null){
            return RespBean.success("帐户已恢复");
        }else {
            return RespBean.error("账户不存在");
        }
    }

    @ApiOperation(value = "添加教师")
    @GetMapping("/teacher/add/{id}")
    public RespBean addTeacher(@PathVariable Integer id){
        if(id==null){
            return RespBean.error("id不能为空");
        }
        User user = userService.addTeacher(id);
        if(user!=null && user.getRoleId().equals(UserTypeEnum.Teacher.getCode())){
            return RespBean.success("添加教师成功",user);
        }else{
            return RespBean.error("用户不存在");
        }
    }

    @ApiOperation(value = "移除教师")
    @GetMapping("/teacher/remove/{id}")
    public RespBean removeTeacher(@PathVariable Integer id){
        if(id==null){
            return RespBean.error("id不能为空");
        }
        User user = userService.deleteTeacher(id);
        if(user!=null){
            return RespBean.success("移除教师成功");
        }else{
            return RespBean.error("用户不存在");
        }
    }
    @ApiOperation(value = "分配角色")
    @PostMapping("/user/role")
    public RespBean allocateRole(@RequestBody User param){
        if(param.getId()==null||param.getRoleId()==null){
            return RespBean.error("参数不能为空");
        }
        User user = userService.getById(param.getId());
        if(user.equals(null)||UserTypeEnum.fromCode(param.getRoleId()).equals(null)){
            return RespBean.error("用户不存在,无法分配");
        }
        user.setRoleId(param.getRoleId());
        userService.updateById(user);
        return RespBean.success("分配角色成功");
    }

    @ApiOperation(value = "权限分配")
    @PostMapping("/permission/allocate")
    public RespBean allocatePermission(@RequestBody AllocPermissionParam model){
        if(model.getRoleId()==null||model.getPermissionIds().isEmpty()){
            return RespBean.error("分配失败");
        }
        rolePermissionService.allocPermission(model.getRoleId(),model.getPermissionIds());
        return RespBean.success("分配成功");
    }

    @ApiOperation(value = "系统信息")
    @GetMapping("/monitor/server")
    public RespBean systemInfo(){
        return RespBean.success("查询系统信息",monitorService.getServeInfo());
    }
}
