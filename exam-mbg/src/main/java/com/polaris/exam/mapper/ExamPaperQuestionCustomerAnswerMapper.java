package com.polaris.exam.mapper;

import com.polaris.exam.dto.QuestionFalseType;
import com.polaris.exam.pojo.ExamPaperQuestionCustomerAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 试卷题目答案表 Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface ExamPaperQuestionCustomerAnswerMapper extends BaseMapper<ExamPaperQuestionCustomerAnswer> {
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
