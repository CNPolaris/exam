package com.polaris.exam.controller.teacher;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.analysis.StatisticsRequest;
import com.polaris.exam.dto.paper.ExamPaperAnswerPageResponse;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.Subject;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.ExamUtil;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api("成绩分析模块")
@RestController("TeacherScoreAnalysisController")
@RequestMapping("/api/exam/analysis")
public class ScoreAnalysisController {
    private final IClassUserService classUserService;
    private final IClassService classService;
    private final IExamPaperAnswerService examPaperAnswerService;
    private final ISubjectService subjectService;
    private final IUserService userService;
    public ScoreAnalysisController(IClassUserService classUserService, IClassService classService, IExamPaperAnswerService examPaperAnswerService, ISubjectService subjectService, IUserService userService) {
        this.classUserService = classUserService;
        this.classService = classService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
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
        Map<String, Object> response = new HashMap<>(11);
        response.put("shouldAttend",classUserService.selectStudentCount(model.getClassId()));

        return RespBean.success("成功",response);
    }
}
