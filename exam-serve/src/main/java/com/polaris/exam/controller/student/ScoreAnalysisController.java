package com.polaris.exam.controller.student;

import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.service.IExamPaperAnswerService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "成绩分析模块", tags = "学生端成绩分析模块")
@RestController("StudentScoreAnalysisController")
@RequestMapping("/api/student/analysis")
public class ScoreAnalysisController {
    private final IUserService userService;
    private final IExamPaperAnswerService examPaperAnswerService;

    public ScoreAnalysisController(IUserService userService, IExamPaperAnswerService examPaperAnswerService) {
        this.userService = userService;
        this.examPaperAnswerService = examPaperAnswerService;
    }

    @ApiOperation("获取学生按照时间排序的考试结果")
    @PostMapping("/result/{id}")
    public RespBean getStudentResultList(@PathVariable Integer id){
        List<ExamPaperAnswer> answerList = examPaperAnswerService.getPaperAnswerByStudentId(id);
        if(answerList==null){
            return RespBean.error("考生考试结果为空", null);
        }
        return RespBean.success("获取考试结果成功", answerList);
    }
}
