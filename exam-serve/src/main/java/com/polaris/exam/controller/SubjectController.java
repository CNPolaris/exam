package com.polaris.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Subject;
import com.polaris.exam.service.ISubjectService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 * 学科表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Api(value = "学科管理模块",tags = "SubjectController")
@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final ISubjectService subjectService;

    @Autowired
    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @ApiOperation(value = "获取学科列表")
    @PostMapping("/list")
    public RespBean getAllSubjectList(Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) Integer level){
        Page<Subject> subjectPage = new Page<>(page, limit);
        return subjectService.getAllSubjectList(subjectPage,level);
    }
    @ApiOperation(value = "添加学科")
    @PostMapping("/create")
    public RespBean create(@RequestBody Subject param){
        return subjectService.create(param);
    }

    @ApiOperation(value = "删除学科")
    @GetMapping("/delete/{id}")
    public RespBean delete(@PathVariable Integer id){
        return subjectService.delete(id);
    }

    @ApiOperation(value = "更新学科")
    @PostMapping("/update/{id}")
    public RespBean update(@PathVariable Integer id, @RequestBody Subject param){
        return subjectService.update(id, param);
    }

    @ApiOperation(value = "查询学科")
    @GetMapping("/select/{id}")
    public RespBean search(@PathVariable Integer id){
        return subjectService.search(id);
    }

    @ApiOperation(value = "修改学科状态")
    @GetMapping("/status/{id}")
    public RespBean updateSubjectStatus(@PathVariable Integer id, @RequestParam Integer status){
        return RespBean.success("成功", subjectService.updateSubjectStatus(id, status));
    }

    @ApiOperation(value = "获取学科")
    @GetMapping("/name")
    public RespBean updateSubjectStatus(){
        return RespBean.success("成功", subjectService.subjectNameList());
    }

    @ApiOperation(value = "获取全部学科")
    @GetMapping("/all")
    public RespBean allSubjectList() {
        return RespBean.success("成功", subjectService.allSubjectList());
    }
}
