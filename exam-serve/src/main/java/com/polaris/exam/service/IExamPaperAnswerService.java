package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.AnswerRequest;
import com.polaris.exam.dto.analysis.StatisticsRequest;
import com.polaris.exam.dto.analysis.StatisticsResponse;
import com.polaris.exam.dto.paper.ExamPaperAnswerInfo;
import com.polaris.exam.dto.paper.ExamPaperAnswerPage;
import com.polaris.exam.dto.paper.ExamPaperAnswerTeacherPageRequest;
import com.polaris.exam.dto.paper.ExamPaperSubmit;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.pojo.ExamPaperQuestionCustomerAnswer;
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
     * 获取学生个人考试记录
     * @param page Page<ExamPaperAnswer>
     * @param model ExamPaperAnswerPage
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> studentPage(Page<ExamPaperAnswer>page,ExamPaperAnswerPage model);

    /**
     * 教师获取班级学生提交的考试记录
     * @param model ExamPaperAnswerPage
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> getStudentRecordPage(ExamPaperAnswerPage model);
    /**
     * 获取全部考试记录
     * @param page Page<ExamPaperAnswer>
     * @param model ExamPaperAnswerPage
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> allStudentPage(Page<ExamPaperAnswer>page,ExamPaperAnswerPage model);

    /**
     * 获取指定班级的学生答卷
     * @param model StatisticsRequest
     * @param studentIds List<Integer>
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> getStudentResultPage(StatisticsRequest model, List<Integer> studentIds);

    /**
     * 统计相关信息
     * @param model StatisticsRequest
     * @param studentIds List<Integer>
     * @return StatisticsRequest
     */
    StatisticsResponse getStatisticsInfo(StatisticsRequest model, List<Integer> studentIds);

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
    /**
     * 教师根据班级和试卷id查询考试记录
     * @param model ExamPaperAnswerTeacherPageRequest
     * @param page Page<ExamPaperAnswer>
     * @return Page<ExamPaperAnswer>
     */
    Page<ExamPaperAnswer> getAnswerByClassAndPaper(ExamPaperAnswerTeacherPageRequest model, Page<ExamPaperAnswer> page);
    /**
     * 根据学生id获取所有的考试记录
     * @param id Integer
     * @return List<ExamPaperAnswer>
     */
    List<ExamPaperAnswer> getPaperAnswerByStudentId(Integer id);

}
