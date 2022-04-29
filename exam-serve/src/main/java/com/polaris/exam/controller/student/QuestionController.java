package com.polaris.exam.controller.student;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.QuestionFalseType;
import com.polaris.exam.dto.paper.ExamPaperSubmitItem;
import com.polaris.exam.dto.question.*;
import com.polaris.exam.enums.QuestionTypeEnum;
import com.polaris.exam.pojo.*;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.ExamUtil;
import com.polaris.exam.utils.HtmlUtil;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "题目管理模块",tags = "学生端")
@RestController("StudentQuestionController")
@RequestMapping("/api/student/question")
public class QuestionController {
    private final IQuestionService questionService;
    private final IUserService userService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final ITextContentService textContentService;
    private final AdminCacheService cacheService;
    private final ISubjectService subjectService;
    @Autowired
    public QuestionController(IQuestionService questionService, IUserService userService, IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, ITextContentService textContentService, AdminCacheService cacheService, ISubjectService subjectService) {
        this.questionService = questionService;
        this.userService = userService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.textContentService = textContentService;
        this.cacheService = cacheService;
        this.subjectService = subjectService;
    }
    @ApiOperation(value = "获取试卷错题列表")
    @GetMapping("/false/{id}")
    public RespBean falseQuestionList(Principal principal, @PathVariable Integer id){
        User user = userService.getUserByUsername(principal.getName());
        List<Integer> falseQuestionIds = examPaperQuestionCustomerAnswerService.selectFalseQuestionIds(id, user.getId());

        List<Question> questionList = questionService.selectQuestionList(falseQuestionIds);
        return RespBean.success("成功",questionList);
    }
    @ApiOperation(value = "获取学生的错题")
    @PostMapping("/false/list")
    public RespBean studentFalseQuestion(Principal principal,@RequestBody QuestionPageStudentRequest model){
        User user = userService.getUserByUsername(principal.getName());
        model.setCreateUser(user.getId());
        Page<ExamPaperQuestionCustomerAnswer> ePage = new Page<>(model.getPage(), model.getLimit());
        Page<ExamPaperQuestionCustomerAnswer> studentPage = examPaperQuestionCustomerAnswerService.studentPage(model, ePage);
        HashMap<String, Object> reMap = new HashMap<>(2);
        reMap.put("total",studentPage.getTotal());
        ArrayList<QuestionPageStudentResponse> qFalseList = new ArrayList<>();
        studentPage.getRecords().forEach(q ->{
            Subject subject = subjectService.getById(q.getSubjectId());
            QuestionPageStudentResponse questionPageStudentResponse = BeanUtil.copyProperties(q, QuestionPageStudentResponse.class);
            questionPageStudentResponse.setCreateTime(q.getCreateTime().toString());
            TextContent textContent = textContentService.selectById(q.getQuestionTextContentId());
            QuestionObject questionObject = JSONUtil.toBean(textContent.getContent(),QuestionObject.class);
            String clear = HtmlUtil.clear(questionObject.getTitleContent());
            questionPageStudentResponse.setShortTitle(clear);
            questionPageStudentResponse.setSubjectName(subject.getName());
            qFalseList.add(questionPageStudentResponse);
        });
        reMap.put("list", qFalseList);
        reMap.put("questionCorrectCount", examPaperQuestionCustomerAnswerService.getQuestionTypeCorrectCount(user.getId()));
        reMap.put("questionTypeCount", examPaperQuestionCustomerAnswerService.getQuestionTypeCount(user.getId()));
        return  RespBean.success("获取错题成功", reMap);
    }
    @ApiOperation(value = "选择题目答案")
    @PostMapping("/answer/select/{id}")
    public RespBean selectAnswer(Principal principal, @PathVariable Integer id){
        QuestionAnswer model = new QuestionAnswer();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.getById(id);
        ExamPaperSubmitItem questionAnswer = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToModel(examPaperQuestionCustomerAnswer);
        QuestionEditRequest question = questionService.getQuestionEditRequest(examPaperQuestionCustomerAnswer.getQuestionId());
        model.setQuestion(question);
        model.setQuestionAnswer(questionAnswer);
        return RespBean.success("成功", model);
    }

    /**
     * 验证题目参数
     * @param model 题目参数
     * @return Boolean
     */
    private boolean validQuestionEditRequest(QuestionEditRequest model){
        int qType = model.getQuestionType().intValue();
        boolean requireCorrect = qType == QuestionTypeEnum.SingleChoice.getCode() || qType == QuestionTypeEnum.TrueFalse.getCode();
        if(requireCorrect){
            if(StrUtil.isBlank(model.getCorrect())){
                return false;
            }
        }
        if(qType == QuestionTypeEnum.GapFilling.getCode()){
            Integer fillSumScore = model.getItems().stream().mapToInt(d -> ExamUtil.scoreFromVM(d.getScore())).sum();
            Integer questionScore = ExamUtil.scoreFromVM(model.getScore());
            if (!fillSumScore.equals(questionScore)){
                return false;
            }
        }
        return true;
    }
}
