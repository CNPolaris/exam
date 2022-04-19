package com.polaris.exam.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.classes.ClassRequest;
import com.polaris.exam.dto.user.UserResponse;
import com.polaris.exam.pojo.Class;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IClassService;
import com.polaris.exam.service.IClassUserService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Api(value = "班级管理模块", tags = "管理员端")
@RestController("AdminClassController")
@RequestMapping("/api/admin/class")
public class ClassController {
    private final IClassService classService;
    private final IClassUserService classUserService;
    private final IUserService userService;
    public ClassController(IClassService classService, IClassUserService classUserService, IUserService userService) {
        this.classService = classService;
        this.classUserService = classUserService;
        this.userService = userService;
    }

    @ApiOperation(value = "班级列表")
    @PostMapping("/list")
    public RespBean classPage(@RequestParam(defaultValue = "1")Integer page, @RequestParam(defaultValue = "10")Integer limit, @RequestParam(required = false)String name){
        Page<Class> classPage = new Page<>(page, limit);
        Page<Class> dataPage = classService.classPage(classPage,name);
        Map<String, Object> response = new HashMap<>();

        response.put("total",dataPage.getTotal());
        response.put("list",dataPage.getRecords());
        return RespBean.success("成功",response);
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

    @ApiOperation(value = "删除班级")
    @GetMapping("/delete/{id}")
    public RespBean deleteClass(@PathVariable Integer id){
        classService.deleteById(id);
        classUserService.deleteClassUserByClass(id);
        return RespBean.success("成功");
    }

    @ApiOperation(value = "添加或编辑班级")
    @PostMapping("/edit")
    public RespBean editClass( @RequestBody ClassRequest model){
        Class aClass = classService.editClass(model);
        return RespBean.success("成功", aClass);
    }
}
