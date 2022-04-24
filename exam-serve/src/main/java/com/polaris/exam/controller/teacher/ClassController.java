package com.polaris.exam.controller.teacher;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public RespBean classList(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        if(UserTypeEnum.Teacher.getCode()!=user.getRoleId()){
            return RespBean.error("无权访问");
        }
        return RespBean.success("获取教师管理班级信息成功",classService.getClassByTeacherId(user.getId()));
    }

    @ApiOperation("分页获取班级列表")
    @PostMapping("/page")
    public RespBean classPage(Principal principal, @RequestBody ClassRequest model){
        User user = userService.getUserByUsername(principal.getName());
        Page<Class> objectPage = new Page<>(model.getPage(), model.getLimit());
        Page<Class> classPage = classService.getClassByTeacherId(objectPage, user.getId());
        HashMap<String, Object> re = new HashMap<>(2);
        re.put("total",classPage.getTotal());
        re.put("list", classPage.getRecords());
        return RespBean.success("成功",re);
    }

    @ApiOperation(value = "查询班级学生列表")
    @PostMapping("/student/{id}")
    public RespBean classStudentList(@PathVariable Integer id,@RequestBody ClassRequest model){
        List<Integer> userIds = classUserService.selectStudentIdByClassId(id);
        Page<User> objectPage = new Page<>(model.getPage(), model.getLimit());
        Page<User> userPage = userService.selectByIds(objectPage, userIds);
        List<UserResponse> userResponseList = userPage.getRecords().stream()
                .map(user -> BeanUtil.toBean(user, UserResponse.class))
                .collect(Collectors.toList());
        Map<String, Object> re = new HashMap<>(2);
        re.put("total", userPage.getTotal());
        re.put("list", userPage.getRecords());
        return RespBean.success("成功", re);
    }

    @ApiOperation("获取班级学生id用户发送消息")
    @GetMapping("/message/student/{id}")
    public RespBean getMessageClassStudent(@PathVariable Integer id) {
        return RespBean.success(classUserService.selectStudentIdByClassId(id));
    }

    @ApiOperation(value = "添加或编辑班级")
    @PostMapping("/edit")
    public RespBean editClass( @RequestBody ClassRequest model){
        Class aClass = classService.editClass(model);
        return RespBean.success("成功", aClass);
    }

    @ApiOperation("获取试卷id对应的班级")
    @GetMapping("/analyse/list/{id}")
    public RespBean getClassListByPaperId(@PathVariable Integer id){
        List<Class> classes = classService.getClassListByPaperId(id);
        return RespBean.success(classes);
    }
}
