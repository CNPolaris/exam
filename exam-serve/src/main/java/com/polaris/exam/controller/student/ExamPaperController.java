package com.polaris.exam.controller.student;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.ExamPaperEditRequest;
import com.polaris.exam.dto.paper.ExamPaperStudentPageRequest;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "试卷管理模块", tags = "学生端试卷管理模块")
@RestController("StudentExamPaperController")
@RequestMapping("/api/student/exam")
public class ExamPaperController {
    private final IExamPaperService examPaperService;
    private final IUserService userService;
    private final IExamPaperAnswerService examPaperAnswerService;
    private final IClassUserService classUserService;
    private final ISubjectService subjectService;
    private final AdminCacheService cacheService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;

    public ExamPaperController(IExamPaperService examPaperService, IUserService userService,
                               IExamPaperAnswerService examPaperAnswerService, IClassUserService classUserService,
                               ISubjectService subjectService, AdminCacheService cacheService,
                               IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService) {
        this.examPaperService = examPaperService;
        this.userService = userService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.classUserService = classUserService;
        this.subjectService = subjectService;
        this.cacheService = cacheService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
    }
    @ApiOperation(value = "学生考试")
    @GetMapping("/do/{id}")
    public RespBean doExamPaper(@PathVariable Integer id, Principal principal){
        if(cacheService.hasDoingPaper(principal.getName(), id)){
            return RespBean.success("成功",cacheService.getDoingPaper(principal.getName(), id));
        } else {
            ExamPaperEditRequest examPaperEditRequest = examPaperService.examPaperToModel(id);
            cacheService.setDoingPaper(principal.getName(), id,examPaperEditRequest.getSuggestTime(),examPaperEditRequest);
            return RespBean.success("成功",examPaperEditRequest);
        }
    }
    @ApiOperation("获取学生所属的试卷")
    @PostMapping("/paper/own")
    public RespBean getUserPaper(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<ExamPaper> userPaper = examPaperService.getUserPaper(user.getId());
        return RespBean.success("获取成功",userPaper);
    }

    @ApiOperation("试卷中心")
    @PostMapping("/paper/page")
    public RespBean getStudentPaperPage(Principal principal, @RequestBody ExamPaperStudentPageRequest model){
        User user = userService.getUserByUsername(principal.getName());
        Page<ExamPaper> studentPage = examPaperService.getStudentPage(user.getId(), model);
        if(studentPage==null){
            return RespBean.error("查询结果为空");
        }
        Map<String, Object> response = new HashMap<>(2);
        response.put("list", studentPage.getRecords());
        response.put("total", studentPage.getTotal());
        return RespBean.success(response);
    }

//    @ApiOperation("任务试卷中心")
//    @PostMapping("/paper/task")
//    public RespBean getStudentTaskPaperPage(@RequestBody ExamPaperStudentPageRequest model) {
//        model.setPaperType(ExamPaperTypeEnum.Task.getCode());
//        Page<ExamPaper> studentTaskPaper = examPaperService.getStudentTaskPaper(model);
//        Map<String, Object> response = new HashMap<>(2);
//        response.put("total", studentTaskPaper.getTotal());
//        response.put("list", studentTaskPaper.getRecords());
//        return RespBean.success(response);
//    }

    @ApiOperation(value = "选择试卷")
    @GetMapping("/select/{id}")
    public RespBean selectExamPaper(@PathVariable Integer id) {
        ExamPaperEditRequest examPaperEditRequest = examPaperService.examPaperToModel(id);
        return RespBean.success("成功", examPaperEditRequest);
    }
    @ApiOperation("获取任务试卷")
    @PostMapping("/paper/task")
    public RespBean getTaskPaper(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<ExamPaper> taskPaper = examPaperService.getTaskPaper(user.getId(), ExamPaperTypeEnum.Task.getCode());
        return RespBean.success("获取任务试卷成功",taskPaper);
    }
}
