package com.polaris.exam.controller.teacher;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.*;
import com.polaris.exam.enums.ExamPaperAnswerStatusEnum;
import com.polaris.exam.enums.ExamPaperTypeEnum;
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
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "试卷答案管理模块",tags = "教师端")
@RestController("TeacherAnswerController")
@RequestMapping("/api/teacher/exam/answer")
public class ExamPaperAnswerController {
    private final IExamPaperAnswerService examPaperAnswerService;
    private final ISubjectService subjectService;
    private final IUserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final IExamPaperService examPaperService;
    public ExamPaperAnswerController(IExamPaperAnswerService examPaperAnswerService, ISubjectService subjectService, IUserService userService, ApplicationEventPublisher eventPublisher, IExamPaperService examPaperService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.examPaperService = examPaperService;
    }

    @ApiOperation(value = "获取班级某张试卷的考试记录")
    @PostMapping("/record")
    public RespBean getRecord(@RequestBody ExamPaperAnswerTeacherPageRequest model){
        Page<ExamPaperAnswer> objectPage = new Page<>(model.getPage(), model.getLimit());
        Page<ExamPaperAnswer> answerPage = examPaperAnswerService.getAnswerByClassAndPaper(model, objectPage);
        Map<String, Object> response = new HashMap<>();
        List<ExamPaperAnswerPageResponse> examPaperAnswerPageResponseList = new ArrayList<>();
        answerPage.getRecords().forEach(e->{
            ExamPaperAnswerPageResponse ep = new ExamPaperAnswerPageResponse();
            Subject subject = subjectService.getById(e.getSubjectId());
            BeanUtil.copyProperties(e,ep);
            ep.setPaperTypeStr(ExamPaperTypeEnum.fromCode(e.getPaperType()).getName());
            ep.setUserName(userService.getUsernameById(e.getCreateUser()));
            ep.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            ep.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            ep.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            ep.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            ep.setSubjectName(subject.getName());
            ep.setCreateTime(e.getCreateTime().toString());
            examPaperAnswerPageResponseList.add(ep);
        });
        response.put("total",answerPage.getTotal());
        response.put("list",examPaperAnswerPageResponseList);
        return RespBean.success("成功", response);
    }
    @ApiOperation(value = "教师阅卷")
    @PostMapping("/edit")
    public RespBean edit(Principal principal, @RequestBody @Valid ExamPaperSubmit examPaperSubmit){
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


    @ApiOperation("教师查看班级学生提交的试卷")
    @PostMapping("/record/list")
    public RespBean getStudentRecordList(Principal principal, @RequestBody ExamPaperAnswerPage model){
        Page<ExamPaperAnswer> studentRecordPage = examPaperAnswerService.getStudentRecordPage(model);
        Map<String,Object> response = new HashMap<>(model.getLimit());
        List<ExamPaperAnswerPageResponse> answerPageResponseList = new ArrayList<>();

        studentRecordPage.getRecords().forEach(e->{
            ExamPaperAnswerPageResponse ep = BeanUtil.copyProperties(e, ExamPaperAnswerPageResponse.class);
            ep.setPaperTypeStr(ExamPaperTypeEnum.fromCode(e.getPaperType()).getName());
            ep.setUserName(userService.getById(e.getCreateUser()).getRealName());
            ep.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            ep.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            ep.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            ep.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            ep.setSubjectName(subjectService.getById(e.getSubjectId()).getName());
            ep.setCreateTime(e.getCreateTime().toString());
            answerPageResponseList.add(ep);
        });
        response.put("list", answerPageResponseList);
        response.put("total", studentRecordPage.getTotal());
        return RespBean.success("成功", response);
    }

    @ApiOperation("教师阅卷")
    @GetMapping("/read/{id}")
    public RespBean read(@PathVariable Integer id){
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.getById(id);
        ExamPaperRead read = new ExamPaperRead();
        ExamPaperEditRequest paper = examPaperService.examPaperToModel(examPaperAnswer.getExamPaperId());
        ExamPaperSubmit answer = examPaperAnswerService.examPaperAnswerToModel(examPaperAnswer.getId());
        read.setPaper(paper);
        read.setAnswer(answer);
        return RespBean.success(read);
    }
}
