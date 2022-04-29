package com.polaris.exam.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.QuestionFalseType;
import com.polaris.exam.dto.paper.ExamPaperAnswerTeacherPageRequest;
import com.polaris.exam.dto.paper.ExamPaperAnswerUpdate;
import com.polaris.exam.dto.paper.ExamPaperSubmitItem;
import com.polaris.exam.dto.question.QuestionPageStudentRequest;
import com.polaris.exam.enums.QuestionTypeEnum;
import com.polaris.exam.pojo.ExamPaperQuestionCustomerAnswer;
import com.polaris.exam.mapper.ExamPaperQuestionCustomerAnswerMapper;
import com.polaris.exam.pojo.TextContent;
import com.polaris.exam.service.IClassUserService;
import com.polaris.exam.service.IExamPaperQuestionCustomerAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.service.ITextContentService;
import com.polaris.exam.utils.ExamUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

/**
 * <p>
 * 试卷题目答案表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class ExamPaperQuestionCustomerAnswerServiceImpl extends ServiceImpl<ExamPaperQuestionCustomerAnswerMapper, ExamPaperQuestionCustomerAnswer> implements IExamPaperQuestionCustomerAnswerService {

    private final ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper;
    private final ITextContentService textContentService;
    private final IClassUserService classUserService;
    public ExamPaperQuestionCustomerAnswerServiceImpl(ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper, ITextContentService textContentService, IClassUserService classUserService) {
        this.examPaperQuestionCustomerAnswerMapper = examPaperQuestionCustomerAnswerMapper;
        this.textContentService = textContentService;
        this.classUserService = classUserService;
    }

    @Override
    public int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates) {
        try{
            for(ExamPaperAnswerUpdate e: examPaperAnswerUpdates){
                ExamPaperQuestionCustomerAnswer answer = examPaperQuestionCustomerAnswerMapper.selectById(e.getId());
                answer.setCustomerScore(e.getCustomerScore());
                answer.setDoRight(e.getDoRight());
                examPaperQuestionCustomerAnswerMapper.updateById(answer);
                saveOrUpdate(answer);
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id) {
        return examPaperQuestionCustomerAnswerMapper.selectList(new QueryWrapper<ExamPaperQuestionCustomerAnswer>().eq("exam_paper_answer_id",id));
    }

    @Override
    public ExamPaperSubmitItem examPaperQuestionCustomerAnswerToModel(ExamPaperQuestionCustomerAnswer qa) {
        ExamPaperSubmitItem examPaperSubmitItemVM = new ExamPaperSubmitItem();
        examPaperSubmitItemVM.setId(qa.getId());
        examPaperSubmitItemVM.setQuestionId(qa.getQuestionId());
        examPaperSubmitItemVM.setDoRight(qa.getDoRight());
        examPaperSubmitItemVM.setItemOrder(qa.getItemOrder());
        examPaperSubmitItemVM.setQuestionScore(ExamUtil.scoreToVM(qa.getQuestionScore()));
        examPaperSubmitItemVM.setScore(ExamUtil.scoreToVM(qa.getCustomerScore()));
        setSpecialToModel(examPaperSubmitItemVM, qa);
        return examPaperSubmitItemVM;
    }

    @Override
    public int insert(ExamPaperQuestionCustomerAnswer model) {
        return examPaperQuestionCustomerAnswerMapper.insert(model);
    }

    @Override
    public void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers) {
        saveBatch(examPaperQuestionCustomerAnswers);
    }
    private void setSpecialToModel(ExamPaperSubmitItem examPaperSubmitItemVM, ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case MultipleChoice:
                examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                examPaperSubmitItemVM.setContentArray(ExamUtil.contentToArray(examPaperQuestionCustomerAnswer.getAnswer()));
                break;
            case GapFilling:
                TextContent textContent = textContentService.selectById(examPaperQuestionCustomerAnswer.getTextContentId());
                List<String> correctAnswer = JSONUtil.toList(textContent.getContent(), String.class);
                examPaperSubmitItemVM.setContentArray(correctAnswer);
                break;
            default:
                if (QuestionTypeEnum.needSaveTextContent(examPaperQuestionCustomerAnswer.getQuestionType())) {
                    TextContent content = textContentService.selectById(examPaperQuestionCustomerAnswer.getTextContentId());
                    examPaperSubmitItemVM.setContent(content.getContent());
                } else {
                    examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                }
                break;
        }
    }

    /**
     * 根据试卷id和学生ids获取提交的试卷答案表
     *
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return List<ExamPaperQuestionCustomerAnswer>
     */
    @Override
    public List<ExamPaperQuestionCustomerAnswer> selectByPaperId(Integer paperId, List<Integer> userIds) {
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",paperId).in("create_user",userIds);

        return examPaperQuestionCustomerAnswerMapper.selectList(queryWrapper);
    }

    /**
     * 通过试卷id获取问题id列表
     *
     * @param paperId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> selectQuestionIdsByPaperId(Integer paperId) {
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",paperId);
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerMapper.selectList(queryWrapper);
        HashSet<Integer> questionIds = new HashSet<>();
        examPaperQuestionCustomerAnswers.forEach(e->{
            questionIds.add(e.getQuestionId());
        });
        return ListUtil.toList(questionIds);
    }

    /**
     * 统计试卷题目正确率
     *
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> analyzeQuestionRight(Integer paperId, List<Integer> userIds) {
        HashMap<String, Object> rightMap = new HashMap<>();
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",paperId);

        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerMapper.selectList(queryWrapper);
        HashSet<Integer> questionIdSet = new HashSet<>();
        examPaperQuestionCustomerAnswers.forEach(e->{
            questionIdSet.add(e.getQuestionId());
        });
        List<Integer> questionIds = ListUtil.toList(questionIdSet);
        questionIds.forEach(q->{
            Integer rightCount = examPaperQuestionCustomerAnswerMapper.selectCount(new QueryWrapper<ExamPaperQuestionCustomerAnswer>().eq("exam_paper_id", paperId).in("create_user", userIds).eq("question_id", q).eq("do_right", true));
            rightMap.put(q.toString(), NumberUtil.div(rightCount.floatValue(),userIds.size()));
        });
        return rightMap;
    }

    /**
     * 获取用户某张试卷的错题Id列表
     *
     * @param paperId Integer
     * @param userId  Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> selectFalseQuestionIds(Integer paperId, Integer userId) {
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",paperId).eq("create_user",userId).eq("do_right",false);
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerMapper.selectList(queryWrapper);
        List<Integer> questionIds = new ArrayList<>();
        examPaperQuestionCustomerAnswers.forEach(e->{
            questionIds.add(e.getQuestionId());
        });
        return questionIds;
    }

    @Override
    public Page<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequest model,Page<ExamPaperQuestionCustomerAnswer> epage) {
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_user", model.getCreateUser()).eq("do_right",0);
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        return examPaperQuestionCustomerAnswerMapper.selectPage(epage,queryWrapper);
    }

    @Override
    public Page<ExamPaperQuestionCustomerAnswer> getAnswerByClassAndPaper(ExamPaperAnswerTeacherPageRequest model, Page<ExamPaperQuestionCustomerAnswer> page) {
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        List<Integer> userIds = classUserService.selectStudentIdByClassId(model.getClassId());
        queryWrapper.eq("subject_id",model.getSubjectId());
        queryWrapper.eq("exam_paper_id", model.getPaperId());
        queryWrapper.eq("status", model.getStatus());
        queryWrapper.in("create_user", userIds);
        return examPaperQuestionCustomerAnswerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<QuestionFalseType> getQuestionTypeCorrectCount(Integer id) {
        try{
            return examPaperQuestionCustomerAnswerMapper.getQuestionTypeCorrectCount(id);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<QuestionFalseType> getQuestionTypeCount(Integer id) {
        try{
            return examPaperQuestionCustomerAnswerMapper.getQuestionTypeCount(id);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
