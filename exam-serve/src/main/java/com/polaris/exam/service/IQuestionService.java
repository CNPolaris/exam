package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.StatisticParam;
import com.polaris.exam.dto.question.QuestionEditRequest;
import com.polaris.exam.dto.question.QuestionPageParam;
import com.polaris.exam.pojo.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IQuestionService extends IService<Question> {
    /**
     * 题目分页查询
     * @param page page
     * @param param param
     * @return Page<Question>
     */
    Page<Question> questionPage(Page<Question> page, QuestionPageParam param);
    /**
     * 添加题目
     * @param model 参数
     * @param userId 创建者id
     * @return Question
     */
    Question create(QuestionEditRequest model, Integer userId);

    /**
     * 更新题目
     * @param model 参数
     * @return Question
     */
    Question update(QuestionEditRequest model);

    /**
     * 根据题目id获取题目详情
     * @param questionId 题目id
     * @return QuestionEditRequest
     */
    QuestionEditRequest getQuestionEditRequest(Integer questionId);
    /**
     * 根据题目获取题目详情
     * @param question 题目id
     * @return QuestionEditRequest
     */
    QuestionEditRequest getQuestionEditRequest(Question question);

    /**
     * 根据id获取
     * @param id id
     * @return Question
     */
    Question selectById(Integer id);

    /**
     * 获取题目总数
     * @return int
     */
    int selectQuestionCount();

    /**
     * 获取用户所属的题目数量
     * @param userId Integer
     * @return  int
     */
    int getQuestionCountByUser(Integer userId);

    /**
     * 获取题目列表
     * @param questionIds List<Integer>
     * @return List<Question>
     */
    List<Question> selectQuestionList(List<Integer> questionIds);
    /**
     * 获取最近每天创建的题目数量
     * @return List<StatisticParam>
     */
    List<StatisticParam> getQuestionCreateStatistic();
}
