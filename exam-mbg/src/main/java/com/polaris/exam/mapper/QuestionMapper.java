package com.polaris.exam.mapper;

import com.polaris.exam.dto.StatisticParam;
import com.polaris.exam.pojo.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 题目表 Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 获取最近每天创建的题目数量
     * @return List<StatisticParam>
     */
    List<StatisticParam> getQuestionCreateStatistic();
}
