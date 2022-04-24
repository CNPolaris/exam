package com.polaris.exam.mapper;

import com.polaris.exam.dto.AnalyseParam;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 试卷答案表 Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswer> {
    /**
     * 获取ids中的及格人数
     * @param model GetPassCount
     * @return Integer
     */
    Integer getPassCount(AnalyseParam model);
    /**
     * 获取最高分
     * @param model GetPassCount
     * @return Integer
     */
    Integer getMaxScore(AnalyseParam model);

    /**
     * 获取最低分
     * @param model GetPassCount
     * @return Integer
     */
    Integer getMinScore(AnalyseParam model);

    /**
     * 获取已参加人数
     * @param model AnalyseParam
     * @return Integer
     */
    Integer getAttendCount(AnalyseParam model);

    /**
     * 计算平均分
     * @param model AnalyseParam
     * @return Integer
     */
    Integer getAvgCount(AnalyseParam model);
}
