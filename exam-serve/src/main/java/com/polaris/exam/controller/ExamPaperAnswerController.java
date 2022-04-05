package com.polaris.exam.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.*;
import com.polaris.exam.enums.ExamPaperAnswerStatusEnum;
import com.polaris.exam.event.CalculateExamPaperAnswerCompleteEvent;
import com.polaris.exam.event.UserEvent;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.Subject;
import com.polaris.exam.pojo.User;
import com.polaris.exam.pojo.UserEventLog;
import com.polaris.exam.service.IExamPaperAnswerService;
import com.polaris.exam.service.IExamPaperService;
import com.polaris.exam.service.ISubjectService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.ExamUtil;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

/**
 * <p>
 * 试卷答案表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Api(value = "试卷答案管理模块",tags = "ExamPaperAnswerController")
@RestController
@RequestMapping("/api/exam/answer")
public class ExamPaperAnswerController {
    private final IExamPaperAnswerService examPaperAnswerService;
    private final IUserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final IExamPaperService examPaperService;
    private final ISubjectService subjectService;
    public ExamPaperAnswerController(IExamPaperAnswerService examPaperAnswerService, IUserService userService, ApplicationEventPublisher eventPublisher, IExamPaperService examPaperService, ISubjectService subjectService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.examPaperService = examPaperService;
        this.subjectService = subjectService;
    }

    @ApiOperation(value = "提交答案")
    @PostMapping("/submit")
    public RespBean answerSubmit(Principal principal, @RequestBody @Valid ExamPaperSubmit examPaperSubmit){
        User user = userService.getUserByUsername(principal.getName());
        ExamPaperAnswerInfo examPaperAnswerInfo = examPaperAnswerService.calculateExamPaperAnswer(examPaperSubmit,user);

        if(examPaperAnswerInfo==null){
            return RespBean.error("任务试卷不可重复");
        }

        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        Integer userScore = examPaperAnswer.getUserScore();
        String scoreToVM = ExamUtil.scoreToVM(userScore);

        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(user.getId());
        userEventLog.setUserName(user.getUserName());
        userEventLog.setRealName(user.getRealName());
        userEventLog.setCreateTime(new Date());

        String content = user.getUserName() + " 提交试卷：" + examPaperAnswerInfo.getExamPaper().getName()
                + " 得分：" + scoreToVM
                + " 耗时：" + ExamUtil.secondToVM(examPaperAnswer.getDoTime());

        userEventLog.setContent(content);
        eventPublisher.publishEvent(new CalculateExamPaperAnswerCompleteEvent(examPaperAnswerInfo));
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RespBean.success("成功",scoreToVM);
    }

    @ApiOperation(value = "查看试卷")
    @GetMapping("/read/{id}")
    public RespBean read(@PathVariable Integer id){
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.getById(id);
        ExamPaperRead read = new ExamPaperRead();
        ExamPaperEditRequest paper = examPaperService.examPaperToModel(examPaperAnswer.getExamPaperId());
        ExamPaperSubmit answer = examPaperAnswerService.examPaperAnswerToModel(examPaperAnswer.getId());
        read.setPaper(paper);
        read.setAnswer(answer);
        return RespBean.success("成功",read);
    }

    @ApiOperation(value = "阅卷功能")
    @PostMapping("/edit")
    public RespBean edit(Principal principal,@RequestBody @Valid ExamPaperSubmit examPaperSubmit){
        boolean notJudge = examPaperSubmit.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
        if(notJudge){
            return RespBean.error("有未批改的题目");
        }
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.getById(examPaperSubmit.getId());
        ExamPaperAnswerStatusEnum examPaperAnswerStatusEnum = ExamPaperAnswerStatusEnum.fromCode(examPaperAnswer.getStatus());
        if(examPaperAnswerStatusEnum == ExamPaperAnswerStatusEnum.Complete){
            return RespBean.error("试卷已经完成");
        }
        String judgeScore = examPaperAnswerService.judge(examPaperSubmit);

        User user = userService.getUserByUsername(principal.getName());
        String content = user.getUserName() + " 批改试卷：" + examPaperAnswer.getPaperName() + " 得分：" + judgeScore;
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(user.getId());
        userEventLog.setUserName(user.getUserName());
        userEventLog.setRealName(user.getRealName());
        userEventLog.setContent(content);
        userEventLog.setCreateTime(new Date());
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RespBean.success("成功",judgeScore);
    }

    @ApiOperation(value = "批改列表")
    @PostMapping("/judge/list")
    public RespBean studentPage(Principal principal, @RequestBody ExamPaperAnswerPage model){
        Page<ExamPaperAnswer> objectPage = new Page<>(model.getPage(), model.getLimit());
        Page<ExamPaperAnswer> examPaperAnswerPage = examPaperAnswerService.paperList(objectPage, model.getSubjectId());
        Map<String, Object> response = new HashMap<>();
        List<ExamPaperAnswerPageResponse> examPaperAnswerPageResponseList = new ArrayList<>();
        examPaperAnswerPage.getRecords().forEach(e->{
            ExamPaperAnswerPageResponse ep = new ExamPaperAnswerPageResponse();
            Subject subject = subjectService.getById(e.getSubjectId());
            BeanUtil.copyProperties(e,ep);
            ep.setUserName(userService.getUsernameById(e.getCreateUser()));
            ep.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            ep.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            ep.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            ep.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            ep.setSubjectName(subject.getName());
            ep.setCreateTime(e.getCreateTime().toString());
            examPaperAnswerPageResponseList.add(ep);
        });
        response.put("total",examPaperAnswerPage.getTotal());
        response.put("list",examPaperAnswerPageResponseList);
        return RespBean.success("成功", response);
    }
    @ApiOperation(value = "批改列表")
    @PostMapping("/complete/list")
    public RespBean complete(@RequestBody ExamPaperAnswerPage model){
        Page<ExamPaperAnswer> objectPage = new Page<>(model.getPage(), model.getLimit());
        Page<ExamPaperAnswer> examPaperAnswerPage = examPaperAnswerService.paperList(objectPage, model.getSubjectId());
        Map<String, Object> response = new HashMap<>();
        List<ExamPaperAnswerPageResponse> examPaperAnswerPageResponseList = new ArrayList<>();
        examPaperAnswerPage.getRecords().forEach(e->{
            ExamPaperAnswerPageResponse ep = new ExamPaperAnswerPageResponse();
            Subject subject = subjectService.getById(e.getSubjectId());
            BeanUtil.copyProperties(e,ep);
            ep.setUserName(userService.getUsernameById(e.getCreateUser()));
            ep.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            ep.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            ep.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            ep.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            ep.setSubjectName(subject.getName());
            ep.setCreateTime(e.getCreateTime().toString());
            examPaperAnswerPageResponseList.add(ep);
        });
        response.put("total",examPaperAnswerPage.getTotal());
        response.put("list",examPaperAnswerPageResponseList);
        return RespBean.success("成功", response);
    }
}
