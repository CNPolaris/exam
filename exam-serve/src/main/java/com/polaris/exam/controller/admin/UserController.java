package com.polaris.exam.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.user.*;
import com.polaris.exam.enums.LevelEnum;
import com.polaris.exam.enums.SexTypeEnum;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.enums.UserTypeEnum;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.AdminCacheService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.CreateUuid;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Api(value = "用户管理模块",tags="UserController")
@RestController("AdminUserController")
@RequestMapping("/api/admin/user")
public class UserController {
    private  final IUserService userService;
    private final AdminCacheService cacheService;
    private final PasswordEncoder passwordEncoder;
    @Value("${type.user.student}")
    private String TYPE_STUDENT;
    @Value("${type.user.teacher}")
    private String TYPE_TEACHER;
    @Value("${type.user.admin}")
    private String TYPE_ADMIN;
    public UserController(IUserService userService, AdminCacheService cacheService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.cacheService = cacheService;
        this.passwordEncoder = passwordEncoder;
    }
    @ApiOperation(value = "获取全部用户列表")
    @PostMapping("/list")
    public RespBean getAllUserList(Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestBody(required = false) User param){
        Page<User> userPage = new Page<>(page, limit);
        JSONObject jsonObject = JSONUtil.parseObj(param);
        if(jsonObject.isEmpty()) {
            return userService.getAllUserList(userPage);
        }
        return userService.getAllUserList(userPage,param);
    }
    @ApiOperation(value = "删除用户")
    @GetMapping("/delete/{id}")
    public RespBean delete(@PathVariable Integer id){
        if(id==null){
            return RespBean.error("id不能为空");
        }else{
            if(userService.deleteById(id)!=0){
                return RespBean.success("删除成功");
            }
            else {
                return RespBean.error("删除失败");
            }
        }
    }
    @ApiOperation(value = "选择用户")
    @GetMapping("/select/{id}")
    public RespBean select(@PathVariable Integer id){
        if(id==null){
            return RespBean.error("id不能为空");
        }else {
            User user = userService.selectById(id);
            if(user==null){
                return RespBean.error("不存在该用户");
            }
            UserResponse userResponse = new UserResponse();
            BeanUtil.copyProperties(user,userResponse);
            userResponse.setSex(SexTypeEnum.fromCode(user.getSex()).getName());
            userResponse.setRole(UserTypeEnum.fromCode(user.getRoleId()).getName());
            userResponse.setStatus(StatusEnum.fromCode(user.getStatus()).getName());
            userResponse.setUserLevel(LevelEnum.fromCode(user.getUserLevel()).getName());
            return RespBean.success("成功",userResponse);
        }
    }


    @ApiOperation(value = "更新用户有效状态")
    @GetMapping("/status/{id}")
    public RespBean updateUserStatus(@PathVariable Integer id, @RequestParam Integer status){
        return RespBean.success("禁用用户成功",userService.updateUserStatus(id, status));
    }

    @ApiOperation("管理员修改其他用户密码")
    @PostMapping("/password/{id}")
    public RespBean updateUserPassword(@PathVariable Integer id, @RequestBody UpdateUserInfo model) {
        if(model.getPassword().isEmpty() || model.getPassword()==null){
            return RespBean.error("密码不能为空");
        }
        User user = userService.updateUserPassword(id, model);
        if(user==null){
            return RespBean.error("用户不存在");
        }
        return RespBean.success("密码更新成功");
    }

    @ApiOperation(value = "获取学生列表")
    @PostMapping("/student/list")
    public RespBean getStudentList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String username){
        HashMap<String, Object> response = new HashMap<>();
        Page<User> objectPage = new Page<>(page, limit);
        Page<User> studentList = userService.getStudentList(objectPage, username);
        response.put("total", studentList.getTotal());
        response.put("data",studentList.getRecords());
        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "获取教师列表")
    @PostMapping("/teacher/list")
    public RespBean getTeacherList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String username){
        HashMap<String, Object> response = new HashMap<>();
        Page<User> objectPage = new Page<>(page, limit);
        Page<User> teacherList = userService.getTeacherList(objectPage, username);

        response.put("total", teacherList.getTotal());
        response.put("data",teacherList.getRecords());
        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "获取管理员列表")
    @PostMapping("/admin/list")
    public RespBean getAdminList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String username){
        HashMap<String, Object> response = new HashMap<>();
        Page<User> objectPage = new Page<>(page, limit);
        Page<User> adminList = userService.getAdminList(objectPage, username);
        response.put("total", adminList.getTotal());
        response.put("data",adminList.getRecords());
        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "根据用户名模糊搜索用户")
    @PostMapping("/selectByUsername")
    public RespBean selectByUsername(@RequestBody String username){
        return RespBean.success("成功",userService.selectUserByUsername(username));
    }

    @ApiOperation(value = "批量上传用户")
    @PostMapping("/upload")
    public RespBean uploadUser(@RequestBody List<UploadUserParam> form){
        userService.uploadUser(form);
        return RespBean.success("上传成功",form);
    }
}
