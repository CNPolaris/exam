package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.*;
import com.polaris.exam.dto.question.QuestionEditRequest;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.mapper.QuestionMapper;
import com.polaris.exam.pojo.*;
import com.polaris.exam.mapper.ExamPaperMapper;
import com.polaris.exam.pojo.Class;
import com.polaris.exam.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.ExamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements IExamPaperService {

    private final ITextContentService textContentService;
    private final ISubjectService subjectService;
    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final IQuestionService questionService;
    private final IClassService classService;
    private final IExamClassService examClassService;
    public ExamPaperServiceImpl(ITextContentService textContentService, ISubjectService subjectService, ExamPaperMapper examPaperMapper, QuestionMapper questionMapper, IQuestionService questionService, IClassService classService, IExamClassService examClassService) {
        this.textContentService = textContentService;
        this.subjectService = subjectService;
        this.examPaperMapper = examPaperMapper;
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.classService = classService;
        this.examClassService = examClassService;
    }

    @Override
    public Page<ExamPaper> examPaperPage(Page<ExamPaper> page, ExamPageParam param) {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        Map<String, Object> beanToMap = BeanUtil.beanToMap(param, true, true);
        queryWrapper.allEq(beanToMap);
        return examPaperMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<ExamPaper> getResultPage(Integer userId, ExamPageParam model) {
        Page<ExamPaper> page = new Page<>(model.getPage(), model.getLimit());
        List<Integer> classIds = classService.getClassIdsByTeacherId(userId);
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();

        if(model.getClassId()!=null){
            List<Integer> paperIds = examPaperMapper.getPaperIdsByClassId(model.getClassId());
            queryWrapper.in("id",paperIds);
            return examPaperMapper.selectPage(page,queryWrapper);
        }

        if(model.getId()!=null){
            queryWrapper.eq("id",model.getId());
        }
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id", model.getSubjectId());
        }
        if(model.getLevel()!=null){
            queryWrapper.eq("grade_level", model.getLevel());
        }
        return examPaperMapper.selectPage(page,queryWrapper);
    }

    /**
     * 获取试卷数量
     *
     * @return int
     */
    @Override
    public int getPaperCount() {
        return examPaperMapper.selectCount(new QueryWrapper<ExamPaper>());
    }

    @Override
    public Integer getExamPaperCountByTeacherId(Integer userId) {
        return examPaperMapper.selectCount(new QueryWrapper<ExamPaper>().eq("create_user", userId));
    }

    /**
     * 获取创建者所属的试卷数量
     *
     * @param userId Integer
     * @return int
     */
    @Override
    public int selectCountByUser(Integer userId) {
        return examPaperMapper.selectCount(new QueryWrapper<ExamPaper>().eq("create_user",userId));
    }

    @Override
    public Page<ExamPaper> myExamPaperPage(Page<ExamPaper> page,Integer userId, Integer subjectId) {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        if(subjectId!=null){
            queryWrapper.eq("create_user",userId).eq("subject_id",subjectId);
        }
        queryWrapper.eq("create_user",userId);
        return examPaperMapper.selectPage(page,queryWrapper);
    }

    @Override
    public ExamPaper selectExamPaper(Integer examId) {
        return examPaperMapper.selectById(examId);
    }

    @Override
    @Transactional
    public ExamPaper saveExamPaper(ExamPaperEditRequest examPaperEditRequest, User user) {
        Date now = new Date();

        List<ExamPaperTitleItem> titleItems = examPaperEditRequest.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromModel(titleItems);
        String frameTextContentStr = JSONUtil.toJsonStr(frameTextContentList);
        ExamPaper examPaper = new ExamPaper();
        BeanUtil.copyProperties(examPaperEditRequest,examPaper);
        TextContent textContent = new TextContent();
        textContent.setContent(frameTextContentStr);
        textContent.setCreateTime(now);
        textContentService.insertTextContent(textContent);

        examPaper.setFrameTextContentId(textContent.getId());
        examPaper.setCreateTime(now);
        examPaper.setCreateUser(user.getId());
        examPaper.setStatus(StatusEnum.OK.getCode());
        examPaperFromModel(examPaperEditRequest,examPaper,titleItems);
        examPaperMapper.insert(examPaper);

        // 如果是班级试卷
        if(examPaperEditRequest.getPaperType()==ExamPaperTypeEnum.Classes.getCode() && !examPaperEditRequest.getClasses().isEmpty()){
            examPaperEditRequest.getClasses().forEach(c -> {
                ExamClass examClass = new ExamClass();
                examClass.setExamId(examPaper.getId());
                examClass.setClassId(c);
                examClass.setStatus(StatusEnum.OK.getCode());
                examClassService.save(examClass);
            });
        }

        return examPaper;
    }

    @Override
    public ExamPaper updateExamPaper(ExamPaperEditRequest examPaperEditRequest) {
        List<ExamPaperTitleItem> titleItems = examPaperEditRequest.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromModel(titleItems);
        String frameTextContentStr = JSONUtil.toJsonStr(frameTextContentList);

        ExamPaper examPaper = examPaperMapper.selectById(examPaperEditRequest.getId());
        TextContent frameTextContent = textContentService.getTextContentById(examPaper.getFrameTextContentId());
        frameTextContent.setContent(frameTextContentStr);
        textContentService.updateById(frameTextContent);
        BeanUtil.copyProperties(examPaperEditRequest,examPaper);
        examPaperFromModel(examPaperEditRequest, examPaper, titleItems);
        examPaperMapper.updateById(examPaper);
        // 如果是班级试卷
        if(examPaperEditRequest.getPaperType()==ExamPaperTypeEnum.Classes.getCode() && !examPaperEditRequest.getClasses().isEmpty()){
            examPaperEditRequest.getClasses().forEach(c -> {
                ExamClass examClass = new ExamClass();
                examClass.setExamId(examPaper.getId());
                examClass.setClassId(c);
                examClass.setStatus(StatusEnum.OK.getCode());
                examClassService.saveOrUpdate(examClass);
            });
        }
        return examPaper;
    }

    @Override
    public ExamPaper deleteExamPaper(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectById(id);
        examPaper.setStatus(StatusEnum.NO.getCode());
//        examPaperMapper.updateById(examPaper);
        textContentService.removeById(examPaper.getFrameTextContentId());
        removeById(id);
        return examPaper;
    }

    @Override
    public ExamPaperEditRequest examPaperToModel(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectById(id);
        ExamPaperEditRequest examPaperEditRequest = new ExamPaperEditRequest();
        BeanUtil.copyProperties(examPaper,examPaperEditRequest);
        examPaperEditRequest.setLevel(examPaper.getGradeLevel());
        TextContent textContent = textContentService.getTextContentById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> titleItemObjects = JSONUtil.toList(textContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = titleItemObjects.stream().
                flatMap(t->t.getQuestionItems().stream().map(q->q.getId()))
                .collect(Collectors.toList());
        List<Question> questions = questionMapper.selectBatchIds(questionIds);
        List<ExamPaperTitleItem> examPaperTitleItem = titleItemObjects.stream().map(t -> {
            ExamPaperTitleItem tTitle = BeanUtil.copyProperties(t, ExamPaperTitleItem.class);
            List<QuestionEditRequest> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequest questionEditRequestVM = questionService.getQuestionEditRequest(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitle.setQuestionItems(questionItemsVM);
            return tTitle;
        }).collect(Collectors.toList());
        examPaperEditRequest.setTitleItems(examPaperTitleItem);
        examPaperEditRequest.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateUtil.formatDateTime(examPaper.getLimitStartTime()), DateUtil.formatDateTime(examPaper.getLimitEndTime()));
            examPaperEditRequest.setLimitDateTime(limitDateTime);
        }
        if(examPaper.getPaperType()==ExamPaperTypeEnum.Classes.getCode()){
            examPaperEditRequest.setClasses(classService.getClassIdsByExamId(examPaper.getId()));
        }
        return examPaperEditRequest;
    }

    /**
     * 更新试卷状态
     *
     * @param id     Integer
     * @param status Integer
     * @return ExamPaper
     */
    @Override
    public ExamPaper updateStatus(Integer id, Integer status) {
        ExamPaper examPaper = getById(id);
        examPaper.setStatus(status);
        updateById(examPaper);
        return examPaper;
    }

    @Override
    public List<ExamPaper> getUserPaper(Integer userId) {
        return examPaperMapper.getUserPaper(userId);
    }

    @Override
    public List<ExamPaper> getPaperByClassId(Integer classId) {
        List<Integer> paperIds = examPaperMapper.getPaperIdsByClassId(classId);
        return examPaperMapper.selectList(new QueryWrapper<ExamPaper>().in("id",paperIds));
    }

    @Override
    public List<ExamPaper> getTaskPaper(Integer userId, Integer type) {
        return examPaperMapper.getTaskPaper(userId, type);
    }

    @Override
    public Page<ExamPaper> getStudentPage(Integer id,ExamPaperStudentPageRequest model) {
        try {
            Page<ExamPaper> page = new Page<>(model.getPage(), model.getLimit());
            QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
            List<Integer> paperIds = examPaperMapper.getPaperIdsToStudent(id);
            queryWrapper.eq("status", StatusEnum.OK.getCode());
            List<Integer> answerIds = examPaperMapper.getAnswerIdsById(id);
            if(answerIds != null && answerIds.size() !=0){
                queryWrapper.notIn("id",answerIds);
            }
            if(model.getPaperType()!=null){
                queryWrapper.eq("paper_type",model.getPaperType());
            }
            if(model.getSubjectId()!=null){
                queryWrapper.eq("subject_id", model.getSubjectId());
            }
            if(paperIds!=null){
                queryWrapper.or().in("id", paperIds);
            }
            return examPaperMapper.selectPage(page,queryWrapper);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Page<ExamPaper> getStudentTaskPaper(ExamPaperStudentPageRequest model) {
        Page<ExamPaper> page = new Page<>(model.getPage(), model.getLimit());
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", StatusEnum.OK.getCode());
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id", model.getSubjectId());
        }
        queryWrapper.eq("paper_type", model.getPaperType());
        return examPaperMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<ExamPaper> teacherGetPaperList(ExamPaperPageRequest model) {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        // 如果年级level不空
        if(model.getLevel()!=null){
            queryWrapper.eq("grade_level",model.getLevel());
        }
        // 如果空
        Class teacherOneClass = classService.getTeacherOneClass(model.getUserId());
        // 如果subjectId不为空
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        // 查询一个学科
        Integer subjectId = classService.getOneTeacherSubject(teacherOneClass.getId());
        queryWrapper.eq("subject_id", subjectId);

        Page<ExamPaper> page = new Page<>(model.getPage(), model.getLimit());
        return examPaperMapper.selectPage(page,queryWrapper);
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromModel(List<ExamPaperTitleItem> titleItems){
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = BeanUtil.copyProperties(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = BeanUtil.copyProperties(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }
    private void examPaperFromModel(ExamPaperEditRequest examPaperEditRequest,ExamPaper examPaper,List<ExamPaperTitleItem> titleItems){
        Integer gradeLevel = subjectService.levelBySubjectId(examPaperEditRequest.getSubjectId());
        Integer questionCount = titleItems.stream().
                mapToInt(t -> t.getQuestionItems().size()).
                sum();
        Integer score = titleItems.stream().
                flatMapToInt(t->t.getQuestionItems().stream().
                        mapToInt(q-> ExamUtil.scoreFromVM(q.getScore()))).
                sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        examPaper.setGradeLevel(gradeLevel);
        List<String> limitDateTime = examPaperEditRequest.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateUtil.parse(limitDateTime.get(0)));
            examPaper.setLimitEndTime(DateUtil.parse(limitDateTime.get(1)));
        }
    }
}
