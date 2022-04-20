package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.question.*;
import com.polaris.exam.enums.QuestionTypeEnum;
import com.polaris.exam.mapper.TextContentMapper;
import com.polaris.exam.pojo.Question;
import com.polaris.exam.mapper.QuestionMapper;
import com.polaris.exam.pojo.TextContent;
import com.polaris.exam.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.service.ISubjectService;
import com.polaris.exam.utils.ExamUtil;
import com.polaris.exam.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    private final QuestionMapper questionMapper;
    private final ISubjectService subjectService;
    private final TextContentMapper textContentMapper;
    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper, ISubjectService subjectService, TextContentMapper textContentMapper) {
        this.questionMapper = questionMapper;
        this.subjectService = subjectService;
        this.textContentMapper = textContentMapper;
    }

    @Override
    public Page<Question> questionPage(Page<Question> page, QuestionPageParam param) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(param!=null){
            Map<String, Object> beanToMap = BeanUtil.beanToMap(param,true,false);
            queryWrapper.allEq(beanToMap,false);
        }
        return questionMapper.selectPage(page, queryWrapper);
    }

    /**
     * 添加题目
     * @param model 参数
     * @param userId 创建者id
     * @return Question
     */
    @Override
    public Question create(QuestionEditRequest model, Integer userId) {
        subjectService.levelBySubjectId(model.getSubjectId());
        Date now = new Date();
        //题干信息
        TextContent infoTextContent = new TextContent();
        infoTextContent.setCreateTime(now);
        setQuestionInfo(infoTextContent,model);
        textContentMapper.insert(infoTextContent);
        //题目信息
        Question question = new Question();
        question.setQuestionType(model.getQuestionType());
        question.setSubjectId(model.getSubjectId());
        question.setCreateUser(userId);
        question.setScore(ExamUtil.scoreFromVM(model.getScore()));
        question.setCorrect(model.getCorrect());
        question.setGradeLevel(model.getGradeLevel());
        question.setStatus(StatusEnum.OK.getCode());
        question.setDifficult(model.getDifficult());
        question.setInfoTextContentId(infoTextContent.getId());
        question.setCreateTime(now);
        questionMapper.insert(question);
        return question;
    }

    /**
     * 根据题目id获取题目详情
     *
     * @param questionId 题目id
     * @return
     */
    @Override
    public QuestionEditRequest getQuestionEditRequest(Integer questionId) {
        //题目映射
        Question question = questionMapper.selectById(questionId);
        return getQuestionEditRequest(question);
    }

    @Override
    public QuestionEditRequest getQuestionEditRequest(Question question) {
        //题目映射
        TextContent questionTextContent = textContentMapper.selectById(question.getInfoTextContentId());
        QuestionObject questionObject = JSONUtil.toBean(questionTextContent.getContent(), QuestionObject.class);
        QuestionEditRequest questionEditRequest = BeanUtil.copyProperties(question, QuestionEditRequest.class);
        questionEditRequest.setTitle(questionObject.getTitleContent());

        //答案
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
        switch (questionTypeEnum){
            case SingleChoice:
            case TrueFalse:
                questionEditRequest.setCorrect(question.getCorrect());
                break;
            case MultipleChoice:
                questionEditRequest.setCorrectArray(ExamUtil.contentToArray(question.getCorrect()));
                break;
            case GapFilling:
                List<String> correctArray = questionObject.getQuestionItemObjects().stream().map(d -> d.getContent()).collect(Collectors.toList());
                questionEditRequest.setCorrectArray(correctArray);
                break;
            case ShortAnswer:
                questionEditRequest.setCorrect(questionObject.getCorrect());
                break;
            default:
                break;
        }
        questionEditRequest.setScore(ExamUtil.scoreToVM(question.getScore()));
        questionEditRequest.setAnalyze(questionObject.getAnalyze());

        //题目项映射
        List<QuestionEditItem> editItems = questionObject.getQuestionItemObjects().stream().map(o -> {
            QuestionEditItem questionEditItem = BeanUtil.copyProperties(o, QuestionEditItem.class);
            if (o.getScore() != null) {
                questionEditRequest.setScore(ExamUtil.scoreToVM(o.getScore()));
            }
            return questionEditItem;
        }).collect(Collectors.toList());
        questionEditRequest.setItems(editItems);
        return questionEditRequest;
    }

    /**
     * 根据id获取
     *
     * @param id id
     * @return Question
     */
    @Override
    public Question selectById(Integer id) {
        return questionMapper.selectById(id);
    }

    /**
     * 获取题目总数
     *
     * @return int
     */
    @Override
    public int selectQuestionCount() {
        return count();
    }

    /**
     * 获取用户所属的题目数量
     *
     * @param userId Integer
     * @return int
     */
    @Override
    public int getQuestionCountByUser(Integer userId) {
        return questionMapper.selectCount(new QueryWrapper<Question>().eq("create_user",userId));
    }

    /**
     * 获取错题列表
     *
     * @param questionIds List<Integer>
     * @return List<Question>
     */
    @Override
    public List<Question> selectQuestionList(List<Integer> questionIds) {
        return questionMapper.selectBatchIds(questionIds);
    }

    /**
     * 更新题目
     *
     * @param model 参数
     * @return Question
     */
    @Override
    public Question update(QuestionEditRequest model) {
        Integer gradeLevel = subjectService.levelBySubjectId(model.getSubjectId());
        Question question = questionMapper.selectById(model.getId());
        question.setSubjectId(model.getSubjectId());
        question.setGradeLevel(gradeLevel);
        question.setScore(ExamUtil.scoreFromVM(model.getScore()));
        question.setDifficult(model.getDifficult());
        question.setCorrect(model.getCorrect());
        updateById(question);

        TextContent textContent = textContentMapper.selectById(question.getInfoTextContentId());
        setQuestionInfo(textContent,model);
        textContentMapper.updateById(textContent);

        return question;
    }

    /**
     * 设置题干信息
     * @param infoTextContent 题干
     * @param model 参数
     */
    public void setQuestionInfo(TextContent infoTextContent, QuestionEditRequest model){
        List<QuestionItemObject> itemObjects = model.getItems().stream().map(i ->
                {
                    QuestionItemObject item = new QuestionItemObject();
                    item.setPrefix(i.getPrefix());
                    item.setContent(i.getContent());
                    item.setItemUuid(i.getItemUuid());
                    item.setScore(ExamUtil.scoreFromVM(i.getScore()));
                    return item;
                }
        ).collect(Collectors.toList());
        QuestionObject questionObject = new QuestionObject();
        questionObject.setQuestionItemObjects(itemObjects);
        questionObject.setAnalyze(model.getAnalyze());
        questionObject.setTitleContent(model.getTitle());
        questionObject.setCorrect(model.getCorrect());
        infoTextContent.setContent(JSONUtil.toJsonStr(questionObject));
    }
}
