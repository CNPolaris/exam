package com.polaris.exam.controller.teacher;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.ExamPaperEditRequest;
import com.polaris.exam.dto.paper.ExamResponse;
import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "试卷管理模块", tags = "教师端")
@RestController("TeacherExamPaperController")
@RequestMapping("/api/teacher/exam")
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

    @ApiOperation(value = "教师自己创建的试卷")
    @GetMapping("/list/my")
    public RespBean myExamList(Principal principal, @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) Integer subjectId) {
        Page<ExamPaper> ePage = new Page<>(page, limit);
        Integer userId = userService.getUserByUsername(principal.getName()).getId();
        Page<ExamPaper> examPaperPage = examPaperService.myExamPaperPage(ePage, userId, subjectId);

        HashMap<String, Object> response = new HashMap<>();
        response.put("total", examPaperPage.getTotal());

        ArrayList<ExamResponse> paperList = new ArrayList<>();
        List<ExamPaper> examPapers = examPaperPage.getRecords();

        for (ExamPaper paper : examPapers) {
            ExamResponse examResponse = BeanUtil.toBean(paper, ExamResponse.class);
            examResponse.setCreateUser(userService.getUsernameById(paper.getCreateUser()));
            paperList.add(examResponse);
        }
        response.put("data", paperList);
        return RespBean.success("成功", response);
    }
    @ApiOperation(value = "教师创建试卷")
    @PostMapping("/create")
    public RespBean createExamPaper(Principal principal, @RequestBody @Valid ExamPaperEditRequest model) {
        ExamPaper examPaper = examPaperService.saveExamPaper(model, userService.getUserByUsername(principal.getName()));
        ExamPaperEditRequest paper = examPaperService.examPaperToModel(examPaper.getId());
        return RespBean.success("成功", paper);
    }
    @ApiOperation(value = "选择查看试卷")
    @GetMapping("/select/{id}")
    public RespBean selectExamPaper(@PathVariable Integer id) {
        ExamPaperEditRequest examPaperEditRequest = examPaperService.examPaperToModel(id);
        return RespBean.success("成功", examPaperEditRequest);
    }
    @ApiOperation(value = "更新试卷")
    @PostMapping("/update")
    public RespBean updateExamPaper(@RequestBody ExamPaperEditRequest model) {
        ExamPaper examPaper = examPaperService.updateExamPaper(model);
        if (examPaper == null) {
            return RespBean.error("试卷不存在");
        }
        ExamPaperEditRequest paper = examPaperService.examPaperToModel(examPaper.getId());
        paper.setClasses(model.getClasses());
        return RespBean.success("成功", paper);
    }

    @ApiOperation(value = "删除试卷")
    @GetMapping("/delete/{id}")
    public RespBean deleteExamPaper(@PathVariable Integer id) {
        ExamPaper examPaper = examPaperService.deleteExamPaper(id);
        if (examPaper == null) {
            return RespBean.error("该试卷不存在");
        }
        return RespBean.success("成功");
    }
}