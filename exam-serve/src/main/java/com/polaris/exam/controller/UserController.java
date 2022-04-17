package com.polaris.exam.controller;


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
@RestController
@RequestMapping("/api/user")
public class UserController {
    private  final IUserService userService;
    private final AdminCacheService cacheService;
    private final PasswordEncoder passwordEncoder;
    @Value("${imgBed.accessToken}")
    private String BED_ACCESS_TOKEN;
    @Value("${imgBed.owner}")
    private String BED_OWNER;
    @Value("${imgBed.repo}")
    private String BED_REPO;
    @Value("${imgBed.path}")
    private String BED_PATH;
    @Value("${imgBed.message}")
    private String BED_MESSAGE;
    @Value("${imgBed.url}")
    private String BED_URL;
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
    @PostMapping("/avatar/save")
    public RespBean uploadAvatar(Principal principal, @RequestBody String url){
        User user = userService.getUserByUsername(principal.getName());

        if(url.isEmpty()){
            return RespBean.error("保存头像失败", user.getAvatar());
        }

        user.setAvatar(url);
        userService.updateById(user);
        cacheService.setUser(user);
        return RespBean.success("更新头像成功",url);
    }

    @ApiOperation(value = "更新密码")
    @PostMapping("/password/edit")
    public RespBean updatePassword(Principal principal,@RequestBody @Valid UpdatePassword param){
        if(param.equals(null)){
            return RespBean.error("不能为空");
        }
        User user = userService.getUserByUsername(principal.getName());
        if(passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
            if (param.getNewPassword().equals(param.getAgainPassword())) {
                user.setPassword(passwordEncoder.encode(param.getNewPassword()));
                userService.updateById(user);
                cacheService.setUser(user);
                return RespBean.success("更新密码成功");
            }
        }
        return RespBean.error("两次输入不相同");
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/update")
    public RespBean updateUserInfo(Principal principal, @RequestBody @Valid UpdateUserInfo info){
        if(info.equals(null)){
            return RespBean.error("内容不能为空");
        }
        User user = userService.updateUserInfo(principal.getName(),info);
        return RespBean.success("更新成功",user);
    }

    @ApiOperation(value = "更新用户有效状态")
    @GetMapping("/status/{id}")
    public RespBean updateUserStatus(@PathVariable Integer id, @RequestParam Integer status){
        return RespBean.success("禁用用户成功",userService.updateUserStatus(id, status));
    }

    @ApiOperation(value = "获取学生列表")
    @PostMapping("/student/list")
    public RespBean getStudentList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String username){
        HashMap<String, Object> response = new HashMap<>();
//        if(cacheService.hasUserList(TYPE_STUDENT,page)&& cacheService.hasUserTotal(TYPE_STUDENT)){
//            response.put("data",cacheService.getUserList(TYPE_STUDENT, page));
//            response.put("total",cacheService.getUserTotal(TYPE_STUDENT));
//            return RespBean.success("成功", response);
//        }
        Page<User> objectPage = new Page<>(page, limit);
        Page<User> studentList = userService.getStudentList(objectPage, username);

        response.put("total", studentList.getTotal());
        response.put("data",studentList.getRecords());
//        cacheService.setUserList(studentList.getRecords(),TYPE_STUDENT,page);
//        if(!cacheService.hasUserTotal(TYPE_STUDENT)){
//            cacheService.setUserTotal(TYPE_STUDENT,studentList.getTotal());
//        }

        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "获取教师列表")
    @PostMapping("/teacher/list")
    public RespBean getTeacherList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String username){
        HashMap<String, Object> response = new HashMap<>();
//        if(cacheService.hasUserList(TYPE_TEACHER,page)&& cacheService.hasUserTotal(TYPE_TEACHER)){
//            response.put("data",cacheService.getUserList(TYPE_TEACHER, page));
//            response.put("total",cacheService.getUserTotal(TYPE_TEACHER));
//            return RespBean.success("成功", response);
//        }
        Page<User> objectPage = new Page<>(page, limit);
        Page<User> teacherList = userService.getTeacherList(objectPage, username);

        response.put("total", teacherList.getTotal());
        response.put("data",teacherList.getRecords());

//        cacheService.setUserList(teacherList.getRecords(),TYPE_TEACHER,page);
//        if(!cacheService.hasUserTotal(TYPE_TEACHER)){
//            cacheService.setUserTotal(TYPE_TEACHER,teacherList.getTotal());
//        }
        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "获取管理员列表")
    @PostMapping("/admin/list")
    public RespBean getAdminList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String username){
        HashMap<String, Object> response = new HashMap<>();
//        if(cacheService.hasUserList(TYPE_ADMIN,page)&& cacheService.hasUserTotal(TYPE_ADMIN)){
//            response.put("data",cacheService.getUserList(TYPE_ADMIN, page));
//            response.put("total",cacheService.getUserTotal(TYPE_ADMIN));
//            return RespBean.success("成功", response);
//        }
        Page<User> objectPage = new Page<>(page, limit);
        Page<User> adminList = userService.getAdminList(objectPage, username);
        response.put("total", adminList.getTotal());
        response.put("data",adminList.getRecords());

//        cacheService.setUserList(adminList.getRecords(),TYPE_ADMIN,page);
//        if(!cacheService.hasUserTotal(TYPE_ADMIN)){
//            cacheService.setUserTotal(TYPE_ADMIN,adminList.getTotal());
//        }

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
