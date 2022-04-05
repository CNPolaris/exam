package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.ExamPaperAnswerInfo;
import com.polaris.exam.dto.paper.ExamPaperAnswerPage;
import com.polaris.exam.dto.paper.ExamPaperSubmit;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.pojo.User;

import java.util.List;

/**
 * <p>
 * 试卷答案表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IExamPaperAnswerService extends IService<ExamPaperAnswer> {
    /**
     * 试卷分数计算
     * @param examPaperSubmit ExamPaperSubmit
     * @param user User
     * @return ExamPaperAnswerInfo
     */
    ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmit examPaperSubmit, User user);

    /**
     * 批改
     * @param examPaperSubmit ExamPaperSubmit
     * @return String
     */
    String judge(ExamPaperSubmit examPaperSubmit);

    /**
     * 答案提交
     * @param id Integer
     * @return ExamPaperSubmit
     */
    ExamPaperSubmit examPaperAnswerToModel(Integer id);

    /**
     * 获取学生答卷
     * @param page Page<ExamPaperAnswer>
     * @param model ExamPaperAnswerPage
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> studentPage(Page<ExamPaperAnswer>page,ExamPaperAnswerPage model);

    /**
     * 批改试卷
     * @param page Page<ExamPaperAnswer>
     * @param subjectId Integer
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> paperList(Page<ExamPaperAnswer> page, Integer subjectId);

    /**
     * 批改完成
     * @param page Integer
     * @param subjectId Page<ExamPaperAnswer>
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> complete(Page<ExamPaperAnswer> page, Integer subjectId);
    /**
     * 通过试卷id以及用户id查询试卷列表
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return List<ExamPaperAnswer>
     */
    List<ExamPaperAnswer> selectByExamIdUserId(Integer paperId,List<Integer> userIds);

    /**
     * 最高分试卷
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return ExamPaperAnswer
     */
    ExamPaperAnswer topExamPaperAnswer(Integer paperId,List<Integer> userIds);
    /**
     * 最低分试卷
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return ExamPaperAnswer
     */
    ExamPaperAnswer lowExamPaperAnswer(Integer paperId,List<Integer> userIds);
}
