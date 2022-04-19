package com.polaris.exam.controller.teacher;

import cn.hutool.core.bean.BeanUtil;
import com.polaris.exam.dto.classes.ClassRequest;
import com.polaris.exam.dto.user.UserResponse;
import com.polaris.exam.enums.UserTypeEnum;
import com.polaris.exam.pojo.Class;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IClassService;
import com.polaris.exam.service.IClassUserService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "班级管理模块", tags = "教师端")
@RestController("TeacherClassController")
@RequestMapping("/api/teacher/class")
public class ClassController {
    private final IClassService classService;
    private final IUserService userService;
    private final IClassUserService classUserService;
    public ClassController(IClassService classService, IUserService userService, IClassUserService classUserService) {
        this.classService = classService;
        this.userService = userService;
        this.classUserService = classUserService;
    }
    @ApiOperation(value = "班级列表")
    @PostMapping("/list")
    public RespBean classPage(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        if(UserTypeEnum.Teacher.getCode()!=user.getRoleId()){
            return RespBean.error("无权访问");
        }
        return RespBean.success("获取教师管理班级信息成功",classService.getClassByTeacherId(user.getId()));
    }
    @ApiOperation(value = "查询班级学生列表")
    @GetMapping("/student/{id}")
    public RespBean classStudentList(@PathVariable Integer id){
        List<Integer> userIds = classUserService.selectStudentIdByClassId(id);
        List<User> users = userService.selectByIds(userIds);
        List<UserResponse> userResponseList = users.stream()
                .map(user -> BeanUtil.toBean(user, UserResponse.class))
                .collect(Collectors.toList());
        return RespBean.success("成功", userResponseList);
    }
    @ApiOperation(value = "添加或编辑班级")
    @PostMapping("/edit")
    public RespBean editClass( @RequestBody ClassRequest model){
        Class aClass = classService.editClass(model);
        return RespBean.success("成功", aClass);
    }
}
