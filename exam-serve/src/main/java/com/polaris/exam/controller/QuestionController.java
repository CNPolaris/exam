package com.polaris.exam.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.question.QuestionEditRequest;
import com.polaris.exam.dto.question.QuestionObject;
import com.polaris.exam.dto.question.QuestionPageParam;
import com.polaris.exam.dto.question.QuestionResponse;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.enums.QuestionTypeEnum;
import com.polaris.exam.mapper.QuestionMapper;
import com.polaris.exam.pojo.Question;
import com.polaris.exam.pojo.TextContent;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.ExamUtil;
import com.polaris.exam.utils.HtmlUtil;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Api(value = "题目管理模块",tags = "QuestionController")
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final IQuestionService questionService;
    private final IUserService userService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final ITextContentService textContentService;
    private final QuestionMapper questionMapper;
    private final AdminCacheService cacheService;
    @Autowired
    public QuestionController(IQuestionService questionService, IUserService userService, IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, ITextContentService textContentService, QuestionMapper questionMapper, AdminCacheService cacheService) {
        this.questionService = questionService;
        this.userService = userService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.textContentService = textContentService;
        this.questionMapper = questionMapper;
        this.cacheService = cacheService;
    }
    @ApiOperation(value = "题目列表")
    @PostMapping("/list")
    public RespBean list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, @RequestBody(required = false) QuestionPageParam param){
        HashMap<String, Object> data = new HashMap<>();
        /*
        判断redis是否有对应题库缓存
         */
        if(cacheService.hasQuestionList(page)){
            data.put("data", cacheService.getQuestionList(page));
            data.put("total",cacheService.getQuestionTotal());
            return RespBean.success("成功",data);
        }

        Page<Question> qPage = new Page<>(page, limit);
        Page<Question> questionPage = questionService.questionPage(qPage, param);

        ArrayList<QuestionResponse> questionList = new ArrayList<>();
        List<Question> questionPageRecords = questionPage.getRecords();

        for(Question question: questionPageRecords){
            QuestionResponse questionResponse = new QuestionResponse();
            BeanUtil.copyProperties(question,questionResponse);
            TextContent textContent = textContentService.getTextContentById(question.getInfoTextContentId());
            QuestionObject questionObject = JSONUtil.toBean(textContent.getContent(),QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            questionResponse.setShortTitle(clearHtml);
            questionList.add(questionResponse);
        }
        data.put("total",questionPage.getTotal());
        data.put("data",questionList);
        cacheService.setQuestionList(questionList,page);
        cacheService.setQuestionTotal(questionPage.getTotal());

        return RespBean.success("成功",data);
    }
    @ApiOperation(value = "添加题目")
    @PostMapping("/create")
    public RespBean create(Principal principal, @RequestBody @Valid QuestionEditRequest model){
        if(!validQuestionEditRequest(model)){
            return RespBean.error("不能为空");
        }
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Question question = questionService.create(model, user.getId());
        if(question==null){
            return RespBean.error("添加题目失败");
        }
        return RespBean.success("添加题目成功");
    }
    @ApiOperation(value = "更新题目")
    @PostMapping("/update")
    public RespBean update(@RequestBody @Valid QuestionEditRequest model){
        if(!validQuestionEditRequest(model)){
            return RespBean.error("参数不能为空");
        }
        Question question = questionService.update(model);
        return RespBean.success("更新成功",question);
    }
    @ApiOperation(value = "编辑题目")
    @PostMapping("/edit")
    public RespBean edit(Principal principal,@RequestBody QuestionEditRequest model){
        if(!validQuestionEditRequest(model)){
            return RespBean.error("参数不能为空");
        }
        if(model.getId()==null){
            questionService.create(model,userService.getUserByUsername(principal.getName()).getId());
        } else {
            questionService.update(model);
        }
        return RespBean.success("编辑题目成功");
    }

    @ApiOperation(value = "选择题目")
    @GetMapping("/select/{id}")
    public RespBean select(@PathVariable Integer id){
        QuestionEditRequest questionEditRequest = questionService.getQuestionEditRequest(id);
        return RespBean.success("成功",questionEditRequest);
    }
    @ApiOperation(value = "删除题目")
    @GetMapping("/delete/{id}")
    public RespBean delete(@PathVariable Integer id){
        Question question = questionService.selectById(id);
        if(question!=null){
            question.setStatus(StatusEnum.NO.getCode());
            questionMapper.updateById(question);
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "获取试卷错题列表")
    @GetMapping("/false/{id}")
    public RespBean falseQuestionList(Principal principal,@PathVariable Integer id){
        User user = userService.getUserByUsername(principal.getName());
        List<Integer> falseQuestionIds = examPaperQuestionCustomerAnswerService.selectFalseQuestionIds(id, user.getId());

        List<Question> questionList = questionService.selectQuestionList(falseQuestionIds);
        return RespBean.success("成功",questionList);
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
