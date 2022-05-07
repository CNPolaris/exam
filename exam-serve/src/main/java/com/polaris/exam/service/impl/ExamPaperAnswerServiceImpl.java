package com.polaris.exam.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.AnalyseParam;
import com.polaris.exam.dto.AnswerRequest;
import com.polaris.exam.dto.analysis.*;
import com.polaris.exam.dto.paper.*;
import com.polaris.exam.dto.task.TaskItemAnswerObject;
import com.polaris.exam.enums.ExamPaperAnswerStatusEnum;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.enums.QuestionTypeEnum;
import com.polaris.exam.mapper.ExamPaperMapper;
import com.polaris.exam.mapper.QuestionMapper;
import com.polaris.exam.mapper.TaskExamCustomerAnswerMapper;
import com.polaris.exam.pojo.*;
import com.polaris.exam.mapper.ExamPaperAnswerMapper;
import com.polaris.exam.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.ExamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷答案表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class ExamPaperAnswerServiceImpl extends ServiceImpl<ExamPaperAnswerMapper, ExamPaperAnswer> implements IExamPaperAnswerService {

    private final ExamPaperMapper examPaperMapper;
    private final ExamPaperAnswerMapper examPaperAnswerMapper;
    private final QuestionMapper questionMapper;
    private final TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
    private final ITextContentService textContentService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final IUserService userService;
    private final IClassUserService classUserService;
    public ExamPaperAnswerServiceImpl(ExamPaperMapper examPaperMapper, ExamPaperAnswerMapper examPaperAnswerMapper, QuestionMapper questionMapper, TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper, ITextContentService textContentService, IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, IUserService userService, IClassUserService classUserService) {
        this.examPaperMapper = examPaperMapper;
        this.examPaperAnswerMapper = examPaperAnswerMapper;
        this.questionMapper = questionMapper;
        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
        this.textContentService = textContentService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.userService = userService;
        this.classUserService = classUserService;
    }

    @Override
    public ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmit examPaperSubmit, User user) {
        Date now = new Date();
        ExamPaperAnswerInfo examPaperAnswerInfo = new ExamPaperAnswerInfo();
        ExamPaper examPaper = examPaperMapper.selectById(examPaperSubmit.getId());
        ExamPaperTypeEnum paperTypeEnum = ExamPaperTypeEnum.fromCode(examPaper.getPaperType());
        //任务试卷只能做一次
        if(paperTypeEnum==ExamPaperTypeEnum.Task){
            ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectOne(new QueryWrapper<ExamPaperAnswer>().eq("id", examPaperSubmit.getId()).eq("create_user", user.getId()));
            if(examPaperAnswer!=null){
                return null;
            }
        }
        String content = textContentService.selectById(examPaper.getFrameTextContentId()).getContent();
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JSONUtil.toList(content, ExamPaperTitleItemObject.class);
        List<Integer> questionIds=examPaperTitleItemObjects.stream().flatMap(t->t.getQuestionItems().stream().map(q->q.getId())).collect(Collectors.toList());
        List<Question> questions = questionMapper.selectBatchIds(questionIds);

        //将题目结构的转化为题目答案
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers=examPaperTitleItemObjects.stream()
                .flatMap(t->t.getQuestionItems().stream()
                        .map(q->{
                            Question question = questions.stream().filter(tq->tq.getId().equals(q.getId())).findFirst().get();
                            ExamPaperSubmitItem customerQuestionAnswer = examPaperSubmit.getAnswerItems().stream()
                                    .filter(tq->tq.getQuestionId().equals(q.getId()))
                                    .findFirst()
                                    .orElse(null);
                            return examPaperQuestionCustomerAnswerFromModel(question, customerQuestionAnswer, examPaper, q.getItemOrder(), user,now);
                        })).collect(Collectors.toList());
        ExamPaperAnswer examPaperAnswer = examPaperAnswerFromModel(examPaperSubmit, examPaper, examPaperQuestionCustomerAnswers, user,now);
        examPaperAnswerInfo.setExamPaper(examPaper);
        examPaperAnswerInfo.setExamPaperAnswer(examPaperAnswer);
        examPaperAnswerInfo.setExamPaperQuestionCustomerAnswers(examPaperQuestionCustomerAnswers);
        return examPaperAnswerInfo;
    }

    @Override
    @Transactional
    public String judge(ExamPaperSubmit examPaperSubmit) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectById(examPaperSubmit.getId());
        List<ExamPaperSubmitItem> judgeItems = examPaperSubmit.getAnswerItems().stream().filter(d -> d.getDoRight() == null).collect(Collectors.toList());
        List<ExamPaperAnswerUpdate> examPaperAnswerUpdates = new ArrayList<>(judgeItems.size());
        Integer customerScore = examPaperAnswer.getUserScore();
        Integer questionCorrect = examPaperAnswer.getQuestionCorrect();
        for (ExamPaperSubmitItem d : judgeItems) {
            ExamPaperAnswerUpdate examPaperAnswerUpdate = new ExamPaperAnswerUpdate();
            examPaperAnswerUpdate.setId(d.getId());
            examPaperAnswerUpdate.setCustomerScore(ExamUtil.scoreFromVM(d.getScore()));
            boolean doRight = examPaperAnswerUpdate.getCustomerScore().equals(ExamUtil.scoreFromVM(d.getQuestionScore()));
            examPaperAnswerUpdate.setDoRight(doRight);
            examPaperAnswerUpdates.add(examPaperAnswerUpdate);
            customerScore += examPaperAnswerUpdate.getCustomerScore();
            if (examPaperAnswerUpdate.getDoRight()) {
                ++questionCorrect;
            }
        }
        examPaperAnswer.setUserScore(customerScore);
        examPaperAnswer.setQuestionCorrect(questionCorrect);
        examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        examPaperAnswerMapper.updateById(examPaperAnswer);
        examPaperQuestionCustomerAnswerService.updateScore(examPaperAnswerUpdates);

        ExamPaperTypeEnum examPaperTypeEnum = ExamPaperTypeEnum.fromCode(examPaperAnswer.getPaperType());
        switch (examPaperTypeEnum) {
            case Task:
                //任务试卷批改完成后，需要更新任务的状态
                ExamPaper examPaper = examPaperMapper.selectById(examPaperAnswer.getExamPaperId());
                Integer taskId = examPaper.getTaskExamId();
                Integer userId = examPaperAnswer.getCreateUser();
                TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerMapper.selectOne(new QueryWrapper<TaskExamCustomerAnswer>().eq("task_id",taskId).eq("user_id",userId));
                TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
                List<TaskItemAnswerObject> taskItemAnswerObjects = JSONUtil.toList(textContent.getContent(), TaskItemAnswerObject.class);
                taskItemAnswerObjects.stream()
                        .filter(d -> d.getExamPaperAnswerId().equals(examPaperAnswer.getId()))
                        .findFirst().ifPresent(taskItemAnswerObject -> taskItemAnswerObject.setStatus(examPaperAnswer.getStatus()));
                textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
                textContentService.updateById(textContent);
                break;
            default:
                break;
        }
        return ExamUtil.scoreToVM(customerScore);
    }

    @Override
    public ExamPaperSubmit examPaperAnswerToModel(Integer id) {
        ExamPaperSubmit examPaperSubmitModel = new ExamPaperSubmit();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectById(id);
        examPaperSubmitModel.setId(examPaperAnswer.getId());
        examPaperSubmitModel.setDoTime(examPaperAnswer.getDoTime());
        examPaperSubmitModel.setScore(ExamUtil.scoreToVM(examPaperAnswer.getUserScore()));
        examPaperSubmitModel.setCreateUser(userService.getUsernameById(examPaperAnswer.getCreateUser()));
        examPaperSubmitModel.setDoTimeStr(ExamUtil.secondToVM(examPaperAnswer.getDoTime()));
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerService.selectListByPaperAnswerId(examPaperAnswer.getId());
        List<ExamPaperSubmitItem> examPaperSubmitItemVMS = examPaperQuestionCustomerAnswers.stream()
                .map(a -> examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToModel(a))
                .collect(Collectors.toList());
        examPaperSubmitModel.setAnswerItems(examPaperSubmitItemVMS);
        return examPaperSubmitModel;
    }

    @Override
    public Page<ExamPaperAnswer> studentPage(Page<ExamPaperAnswer>page,ExamPaperAnswerPage model) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_user", model.getCreateUser());
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        if(model.getStatus()!=null){
            queryWrapper.eq("status", model.getStatus());
        }
        Page<ExamPaperAnswer> examPaperAnswerPage = examPaperAnswerMapper.selectPage(page, queryWrapper);
        return examPaperAnswerPage;
    }

    @Override
    public Page<ExamPaperAnswer> getStudentRecordPage(ExamPaperAnswerPage model) {
        Page<ExamPaperAnswer> page = new Page<>(model.getPage(), model.getLimit());
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id", model.getSubjectId());
        }
        if(model.getPaperId() != null){
            queryWrapper.eq("exam_paper_id",model.getPaperId());
        }
        if(model.getStatus() != null){
            queryWrapper.eq("status", model.getStatus());
        }
        if(model.getCreateUser() != null){
            queryWrapper.eq("create_user", model.getCreateUser());
        }
        return examPaperAnswerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<ExamPaperAnswer> allStudentPage(Page<ExamPaperAnswer> page, ExamPaperAnswerPage model) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id", model.getSubjectId());
        }
        if(model.getPaperId()!=null){
            queryWrapper.eq("exam_paper_id", model.getPaperId());
        }
        if(model.getCreateUser()!=null){
            queryWrapper.eq("create_user",model.getCreateUser());
        }
        return examPaperAnswerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<ExamPaperAnswer> getStudentResultPage(StatisticsRequest model, List<Integer> studentIds) {
        Page<ExamPaperAnswer> page = new Page<>(model.getPage(), model.getLimit());
        return examPaperAnswerMapper.selectPage(page, new QueryWrapper<ExamPaperAnswer>().orderByDesc("user_score").eq("exam_paper_id", model.getPaperId()).in("create_user", studentIds));
    }

    @Override
    public StatisticsResponse getStatisticsInfo(StatisticsRequest model, List<Integer> studentIds) {
        StatisticsResponse response = new StatisticsResponse();
        AnalyseParam analyseParam = new AnalyseParam(model.getPaperId(), studentIds);
        // 应参加考试人数
        response.setShouldAttend(studentIds.size());
        // 已参加人数
        response.setAttended(examPaperAnswerMapper.getAttendCount(analyseParam));
        // 及格人数
        response.setPassCount(examPaperAnswerMapper.getPassCount(analyseParam));
        // 最高低分
        response.setMaxScore(examPaperAnswerMapper.getMaxScore(analyseParam));
        response.setMinScore(examPaperAnswerMapper.getMinScore(analyseParam));
        // 平均分
        response.setAvgScore(examPaperAnswerMapper.getAvgCount(analyseParam));
        return response;
    }

    @Override
    public Page<ExamPaperAnswer> paperList(Page<ExamPaperAnswer> page, Integer subjectId) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        if(subjectId==null){
            return examPaperAnswerMapper.selectPage(page, queryWrapper);
        }
        queryWrapper.eq("subject_id",subjectId);
        return examPaperAnswerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<ExamPaperAnswer> complete(Page<ExamPaperAnswer> page, Integer subjectId) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",ExamPaperAnswerStatusEnum.Complete.getCode());
        if(subjectId==null){
            return examPaperAnswerMapper.selectPage(page, queryWrapper);
        }
        queryWrapper.eq("subject_id",subjectId);
        return examPaperAnswerMapper.selectPage(page,queryWrapper);
    }

    /**
     * 通过试卷id以及用户id查询试卷列表
     *
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return List<ExamPaperAnswer>
     */
    @Override
    public List<ExamPaperAnswer> selectByExamIdUserId(Integer paperId, List<Integer> userIds) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("create_user",userIds).eq("exam_paper_id",paperId);
        return examPaperAnswerMapper.selectList(queryWrapper);
    }

    /**
     * 最高分试卷
     *
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return ExamPaperAnswer
     */
    @Override
    public ExamPaperAnswer topExamPaperAnswer(Integer paperId, List<Integer> userIds) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("create_user",userIds).eq("exam_paper_id",paperId).orderByAsc("user_score");
        return examPaperAnswerMapper.selectList(queryWrapper).stream().findFirst().get();

    }

    /**
     * 最低分试卷
     *
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return ExamPaperAnswer
     */
    @Override
    public ExamPaperAnswer lowExamPaperAnswer(Integer paperId, List<Integer> userIds) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",paperId).in("create_user",userIds).orderByDesc("user_score");
        return examPaperAnswerMapper.selectList(queryWrapper).stream().findFirst().get();
    }

    @Override
    public Page<ExamPaperAnswer> getAnswerByClassAndPaper(ExamPaperAnswerTeacherPageRequest model, Page<ExamPaperAnswer> page) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        List<Integer> userIds = classUserService.selectStudentIdByClassId(model.getClassId());
        queryWrapper.eq("subject_id",model.getSubjectId());
        queryWrapper.eq("exam_paper_id", model.getPaperId());
        queryWrapper.eq("status", model.getStatus());
        queryWrapper.in("create_user", userIds);
        return examPaperAnswerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<ExamPaperAnswer> getPaperAnswerByStudentId(Integer id) {
        try{
            List<ExamPaperAnswer> answerList = examPaperAnswerMapper.selectList(new QueryWrapper<ExamPaperAnswer>().eq("create_user", id).orderByAsc("create_time"));
            if(answerList != null && answerList.size()>0){
                return answerList;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 用户提交答案的转化存储对象
     *
     * @param question               question
     * @param customerQuestionAnswer customerQuestionAnswer
     * @param examPaper              examPaper
     * @param itemOrder              itemOrder
     * @param user                   user
     * @return ExamPaperQuestionCustomerAnswer
     */
    private ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswerFromModel(Question question, ExamPaperSubmitItem customerQuestionAnswer, ExamPaper examPaper, Integer itemOrder, User user,Date now) {
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = new ExamPaperQuestionCustomerAnswer();
        examPaperQuestionCustomerAnswer.setQuestionId(question.getId());
        examPaperQuestionCustomerAnswer.setExamPaperId(examPaper.getId());
        examPaperQuestionCustomerAnswer.setQuestionScore(question.getScore());
        examPaperQuestionCustomerAnswer.setSubjectId(examPaper.getSubjectId());
        examPaperQuestionCustomerAnswer.setItemOrder(itemOrder);
        examPaperQuestionCustomerAnswer.setCreateTime(now);
        examPaperQuestionCustomerAnswer.setCreateUser(user.getId());
        examPaperQuestionCustomerAnswer.setQuestionType(question.getQuestionType());
        examPaperQuestionCustomerAnswer.setQuestionTextContentId(question.getInfoTextContentId());
        if (null == customerQuestionAnswer) {
            examPaperQuestionCustomerAnswer.setCustomerScore(0);
        } else {
            setSpecialFromModel(examPaperQuestionCustomerAnswer, question, customerQuestionAnswer);
        }
        return examPaperQuestionCustomerAnswer;
    }
    /**
     * 判断提交答案是否正确，保留用户提交的答案
     *
     * @param examPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer
     * @param question                        question
     * @param customerQuestionAnswer          customerQuestionAnswer
     */
    private void setSpecialFromModel(ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer, Question question, ExamPaperSubmitItem customerQuestionAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getContent()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case MultipleChoice:
                String customerAnswer = ExamUtil.contentToString(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(customerAnswer);
                examPaperQuestionCustomerAnswer.setDoRight(customerAnswer.equals(question.getCorrect()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case GapFilling:
                String correctAnswer = JSONUtil.toJsonStr(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(correctAnswer);
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
            default:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
        }
    }

    private ExamPaperAnswer examPaperAnswerFromModel(ExamPaperSubmit examPaperSubmit, ExamPaper examPaper, List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers, User user,Date now) {
        Integer systemScore = examPaperQuestionCustomerAnswers.stream().mapToInt(a -> a.getCustomerScore()).sum();
        long questionCorrect = examPaperQuestionCustomerAnswers.stream().filter(a -> a.getCustomerScore().equals(a.getQuestionScore())).count();
        ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
        examPaperAnswer.setPaperName(examPaper.getName());
        examPaperAnswer.setDoTime(examPaperSubmit.getDoTime());
        examPaperAnswer.setExamPaperId(examPaper.getId());
        examPaperAnswer.setCreateUser(user.getId());
        examPaperAnswer.setCreateTime(now);
        examPaperAnswer.setSubjectId(examPaper.getSubjectId());
        examPaperAnswer.setQuestionCount(examPaper.getQuestionCount());
        examPaperAnswer.setPaperScore(examPaper.getScore());
        examPaperAnswer.setPaperType(examPaper.getPaperType());
        examPaperAnswer.setSystemScore(systemScore);
        examPaperAnswer.setUserScore(systemScore);
        examPaperAnswer.setTaskExamId(examPaper.getTaskExamId());
        examPaperAnswer.setQuestionCorrect((int) questionCorrect);
        boolean needJudge = examPaperQuestionCustomerAnswers.stream().anyMatch(d -> QuestionTypeEnum.needSaveTextContent(d.getQuestionType()));
        if (needJudge) {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        } else {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        }
        return examPaperAnswer;
    }
}
