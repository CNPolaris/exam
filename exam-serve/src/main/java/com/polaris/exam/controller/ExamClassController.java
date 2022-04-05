package com.polaris.exam.controller;


import com.polaris.exam.service.IExamClassService;
import com.polaris.exam.utils.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 试卷-班级关联表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@RestController
@RequestMapping("/api/ec")
public class ExamClassController {
    private final IExamClassService examClassService;

    public ExamClassController(IExamClassService examClassService) {
        this.examClassService = examClassService;
    }

    @GetMapping("/class/{id}")
    public RespBean classIdList(@PathVariable Integer id){
        return RespBean.success("chengg",examClassService.getClassIdByExamId(id));
    }
}
