package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.QuestionFalseType;
import com.polaris.exam.dto.paper.ExamPaperAnswerTeacherPageRequest;
import com.polaris.exam.dto.paper.ExamPaperAnswerUpdate;
import com.polaris.exam.dto.paper.ExamPaperSubmitItem;
import com.polaris.exam.dto.question.QuestionPageStudentRequest;
import com.polaris.exam.pojo.ExamPaperQuestionCustomerAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试卷题目答案表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IExamPaperQuestionCustomerAnswerService extends IService<ExamPaperQuestionCustomerAnswer> {
    /**
     * 更新分数
     * @param examPaperAnswerUpdates List<ExamPaperAnswerUpdate>
     * @return int
     */
    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);

    /**
     * 根据试卷答案id获取标准答案
     * @param id Integer
     * @return List<ExamPaperQuestionCustomerAnswer>
     */
    List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    /**
     * 转换题目项目
     * @param qa ExamPaperQuestionCustomerAnswer
     * @return ExamPaperSubmitItem
     */
    ExamPaperSubmitItem examPaperQuestionCustomerAnswerToModel(ExamPaperQuestionCustomerAnswer qa);
    /**
     * 插入单个数据
     * @param model ExamPaperQuestionCustomerAnswer
     * @return int
     */
    int insert(ExamPaperQuestionCustomerAnswer model);

    /**
     * 插入数据列表
     * @param examPaperQuestionCustomerAnswers List<ExamPaperQuestionCustomerAnswer>
     */
    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    /**
     * 根据试卷id和学生ids获取提交的试卷答案表
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return List<ExamPaperQuestionCustomerAnswer>
     */
    List<ExamPaperQuestionCustomerAnswer> selectByPaperId(Integer paperId,List<Integer>userIds);

    /**
     * 通过试卷id获取问题id列表
     * @param paperId Integer
     * @return List<Integer>
     */
    List<Integer> selectQuestionIdsByPaperId(Integer paperId);

    /**
     * 统计试卷题目正确率
     * @param paperId Integer
     * @param userIds List<Integer>
     * @return Map<String,Object>
     */
    Map<String,Object> analyzeQuestionRight(Integer paperId,List<Integer> userIds);

    /**
     * 获取用户某张试卷的错题Id列表
     * @param paperId Integer
     * @param userId Integer
     * @return List<Integer>
     */
    List<Integer> selectFalseQuestionIds(Integer paperId,Integer userId);

    /**
     * 获取学生的试卷错题
     * @param model QuestionPageStudentRequest
     * @return Page<ExamPaperQuestionCustomerAnswer>
     */
    Page<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequest model, Page<ExamPaperQuestionCustomerAnswer> epage);

    /**
     * 教师根据班级和试卷id查询考试记录
     * @param model ExamPaperAnswerTeacherPageRequest
     * @param page Page<ExamPaperQuestionCustomerAnswer>
     * @return Page<ExamPaperQuestionCustomerAnswer>
     */
    Page<ExamPaperQuestionCustomerAnswer> getAnswerByClassAndPaper(ExamPaperAnswerTeacherPageRequest model, Page<ExamPaperQuestionCustomerAnswer> page);
    /**
     * 按照不同题目类型统计错题个数
     * @param id Integer
     * @return List<QuestionFalseType>
     */
    List<QuestionFalseType> getQuestionTypeCorrectCount(Integer id);
    /**
     * 按照不同题目类型统计个数
     * @param id Integer
     * @return List<QuestionFalseType>
     */
    List<QuestionFalseType> getQuestionTypeCount(Integer id);
}
