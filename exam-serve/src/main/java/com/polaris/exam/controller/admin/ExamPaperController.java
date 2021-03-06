package com.polaris.exam.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.ExamPageParam;
import com.polaris.exam.dto.paper.ExamPaperEditRequest;
import com.polaris.exam.dto.paper.ExamResponse;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.enums.LevelEnum;
import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.AnalyzeUtil;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

/**
 * <p>
 * 试卷表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Api(value = "试卷管理模块", tags = "管理员端试卷管理模块")
@RestController("AdminExamPaperController")
@RequestMapping("/api/admin/exam")
public class ExamPaperController {
    private final IExamPaperService examPaperService;
    private final IUserService userService;
    private final IExamPaperAnswerService examPaperAnswerService;
    private final IExamClassService examClassService;
    private final IClassUserService classUserService;
    private final ISubjectService subjectService;
    private final AdminCacheService cacheService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;

    public ExamPaperController(IExamPaperService examPaperService, IUserService userService,
                               IExamPaperAnswerService examPaperAnswerService, IExamClassService examClassService, IClassUserService classUserService,
                               ISubjectService subjectService, AdminCacheService cacheService,
                               IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService) {
        this.examPaperService = examPaperService;
        this.userService = userService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.examClassService = examClassService;
        this.classUserService = classUserService;
        this.subjectService = subjectService;
        this.cacheService = cacheService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
    }

    @ApiOperation(value = "试卷列表")
    @PostMapping("/list")
    public RespBean list(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit, @RequestBody(required = false) ExamPageParam param) {
        Page<ExamPaper> ePage = new Page<>(page, limit);
        Page<ExamPaper> examPaperPage = examPaperService.examPaperPage(ePage, param);

        HashMap<String, Object> response = new HashMap<>();
        response.put("total", examPaperPage.getTotal());

        ArrayList<ExamResponse> paperList = new ArrayList<>();
        List<ExamPaper> examPapers = examPaperPage.getRecords();

        for (ExamPaper paper : examPapers) {
            ExamResponse examResponse = BeanUtil.toBean(paper, ExamResponse.class);
            examResponse.setCreateUser(userService.getUsernameById(paper.getCreateUser()));
            examResponse.setSubjectId(paper.getSubjectId());
            examResponse.setGradeLevel(paper.getGradeLevel());
            paperList.add(examResponse);
        }
        response.put("data", paperList);
        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "管理员创建试卷")
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

    @ApiOperation(value = "成绩分析")
    @GetMapping("/analyze")
    public RespBean analyzeClassExam(@RequestParam Integer paperId, @RequestParam Integer classId) {
        Map<String, Object> response = new HashMap<>();
        if (cacheService.getAnalyzeInfo(paperId, classId) != null) {
            return RespBean.success("成功", cacheService.getAnalyzeInfo(paperId, classId));
        }

        List<Integer> studentIds = classUserService.selectStudentIdByClassId(classId);
        List<ExamPaperAnswer> examPaperAnswerList = examPaperAnswerService.selectByExamIdUserId(paperId, studentIds);
        ExamPaperAnswer topExamPaperAnswer = examPaperAnswerService.topExamPaperAnswer(paperId, studentIds);
        ExamPaperAnswer lowExamPaperAnswer = examPaperAnswerService.lowExamPaperAnswer(paperId, studentIds);
        ExamPaper examPaper = examPaperService.selectExamPaper(paperId);
        Map<String, Object> questionRight = examPaperQuestionCustomerAnswerService.analyzeQuestionRight(paperId,
                studentIds);
        List<Integer> arrayList = new ArrayList<>();
        examPaperAnswerList.forEach(e -> {
            arrayList.add(e.getUserScore());
        });
        double[] scoreArray = AnalyzeUtil.scoreArray(arrayList);
        double mean = StatUtils.mean(scoreArray);
        double variance = StatUtils.variance(scoreArray);
        double[] mode = Arrays.stream(Arrays.stream(StatUtils.mode(scoreArray)).sorted().toArray()).limit(3).toArray();
        Median median = new Median();
        double mid = median.evaluate(scoreArray);
        double percentile = StatUtils.percentile(scoreArray, 80);
        /* 试卷基本信息 */
        response.put("score", examPaper.getScore());
        response.put("paper_type", examPaper.getPaperType());
        response.put("subject", subjectService.getById(examPaper.getSubjectId()).getName());
        response.put("level", LevelEnum.fromCode(examPaper.getGradeLevel()).getName());
        response.put("suggest_time", examPaper.getSuggestTime());
        // 统计信息
        response.put("do_count", studentIds.size());
        response.put("top_score", topExamPaperAnswer.getUserScore());
        response.put("top_user", userService.getUsernameById(topExamPaperAnswer.getCreateUser()));
        response.put("low_score", lowExamPaperAnswer.getUserScore());
        response.put("low_user", userService.getUsernameById(lowExamPaperAnswer.getCreateUser()));
        response.put("mean", mean);
        response.put("variance", variance);
        response.put("mode", mode);
        response.put("mid", mid);
        response.put("percentile", percentile);
        response.put("scoreArray", scoreArray);
        // 题目正确率
        response.put("question_analyze", questionRight);
        // 设置缓存
        cacheService.setAnalyzeInfo(paperId, classId, response);
        return RespBean.success("成功", response);
    }

    @ApiOperation(value = "改变试卷有效状态")
    @GetMapping("/status/{id}")
    public RespBean updatePaperStatus(@PathVariable Integer id, @RequestParam Integer status) {
        ExamPaper examPaper = examPaperService.updateStatus(id, status);
        return RespBean.success("更新试卷有效状态成功", examPaper);
    }

    @ApiOperation("获取用户所属的试卷")
    @PostMapping("/paper/own")
    public RespBean getUserPaper(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<ExamPaper> userPaper = examPaperService.getUserPaper(user.getId());
        return RespBean.success("获取成功",userPaper);
    }

    @ApiOperation("获取任务试卷")
    @PostMapping("/paper/task")
    public RespBean getTaskPaper(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<ExamPaper> taskPaper = examPaperService.getTaskPaper(user.getId(), ExamPaperTypeEnum.Task.getCode());
        return RespBean.success("获取任务试卷成功",taskPaper);
    }
}
