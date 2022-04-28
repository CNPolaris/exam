package com.polaris.exam.controller.teacher;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.analysis.StatisticsStudentResponse;
import com.polaris.exam.dto.analysis.StatisticsRequest;
import com.polaris.exam.dto.analysis.StatisticsResponse;
import com.polaris.exam.dto.paper.ExamPaperAnswerPageResponse;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.pojo.ClassUser;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.Subject;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.ExamUtil;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api("成绩分析模块")
@RestController("TeacherScoreAnalysisController")
@RequestMapping("/api/exam/analysis")
public class ScoreAnalysisController {
    @Value("${score.passRate}")
    private double pass;
    private final IClassUserService classUserService;
    private final IClassService classService;
    private final IExamPaperAnswerService examPaperAnswerService;
    private final ISubjectService subjectService;
    private final IUserService userService;
    private final IExamClassService examClassService;
    public ScoreAnalysisController(IClassUserService classUserService, IClassService classService, IExamPaperAnswerService examPaperAnswerService, ISubjectService subjectService, IUserService userService, IExamClassService examClassService) {
        this.classUserService = classUserService;
        this.classService = classService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.examClassService = examClassService;
    }

    @ApiOperation("成绩分析-考生成绩")
    @PostMapping("/student/list")
    public RespBean getStudentExamList(@RequestBody StatisticsRequest model) {
        List<Integer> studentIds = classService.getStudentIdsByClassId(model.getClassId());
        Page<ExamPaperAnswer> studentResultPage = examPaperAnswerService.getStudentResultPage(model, studentIds);
        HashMap<String, Object> response = new HashMap<>(2);
        ArrayList<ExamPaperAnswerPageResponse> resultList = new ArrayList<>(model.getLimit());
        studentResultPage.getRecords().forEach(e->{
            ExamPaperAnswerPageResponse ep = BeanUtil.copyProperties(e, ExamPaperAnswerPageResponse.class);
            Subject subject = subjectService.getById(e.getSubjectId());
            ep.setPaperTypeStr(ExamPaperTypeEnum.fromCode(e.getPaperType()).getName());
            User user = userService.getById(e.getCreateUser());
            ep.setUserName(user.getRealName());
            ep.setUserAccount(user.getUserName());
            ep.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            ep.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            ep.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            ep.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            ep.setSubjectName(subject.getName());
            ep.setCreateTime(e.getCreateTime().toString());
            resultList.add(ep);
        });
        response.put("list", resultList);
        response.put("total", studentResultPage.getTotal());
        return RespBean.success(response);
    }
    @ApiOperation("成绩分析--简单统计")
    @PostMapping("/statistics")
    public RespBean paperStatistics(@RequestBody StatisticsRequest model){
        List<Integer> studentIds = classService.getStudentIdsByClassId(model.getClassId());
        StatisticsResponse statisticsInfo = examPaperAnswerService.getStatisticsInfo(model, studentIds);
        return RespBean.success("成功",statisticsInfo);
    }

    @ApiOperation("成绩分析-学生考试情况统计")
    @PostMapping("/statistics/student")
    public RespBean statisticsStudent(Principal principal, @RequestBody StatisticsRequest model){
        if(model.getClassId()==null){
            return RespBean.error("参数不能为空");
        }
        Page<ClassUser> classUserPage = classUserService.getStudentIdsByClass(model);
        HashMap<String, Object> response = new HashMap<>(2);
        response.put("total", classUserPage.getTotal());
        int paperCount = examClassService.getPaperCountByClassId(model.getClassId());
        List<StatisticsStudentResponse> studentList = new ArrayList<>(model.getLimit());
        classUserPage.getRecords().forEach(classUser->{
            StatisticsStudentResponse attendResponse = new StatisticsStudentResponse();
            User user = userService.getById(classUser.getUserId());
            BeanUtil.copyProperties(user, attendResponse);
            List<ExamPaperAnswer> answerList = examPaperAnswerService.getPaperAnswerByStudentId(user.getId());
            attendResponse.setShouldAttendCount(paperCount);
            if(answerList!=null){
                attendResponse.setAttendCount(answerList.size());
                AtomicInteger passCount = new AtomicInteger();
                answerList.forEach(answer->{
                    if(answer.getUserScore() >= answer.getPaperScore()*pass){
                        passCount.getAndIncrement();
                    }
                });
                attendResponse.setPassCount(paperCount);
                attendResponse.setQuestionCount(answerList.stream().mapToInt(ExamPaperAnswer::getQuestionCount).sum());
                attendResponse.setCorrectCount(answerList.stream().mapToInt(ExamPaperAnswer::getQuestionCorrect).sum());
                attendResponse.setMaxScore(answerList.stream().mapToInt(ExamPaperAnswer::getUserScore).max().getAsInt());
                attendResponse.setMinScore(answerList.stream().mapToInt(ExamPaperAnswer::getUserScore).min().getAsInt());
                attendResponse.setAvgScore(answerList.stream().mapToLong(ExamPaperAnswer::getUserScore).average().getAsDouble());
            } else {
                attendResponse.setAttendCount(0);
                attendResponse.setPassCount(0);
                attendResponse.setAvgScore(0.0);
                attendResponse.setMinScore(0);
                attendResponse.setMaxScore(0);
                attendResponse.setCorrectCount(0);
                attendResponse.setQuestionCount(0);
            }
            studentList.add(attendResponse);
        });
        response.put("list",studentList);
        return RespBean.success(response);
    }
}
