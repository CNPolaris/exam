package com.polaris.exam.mapper;

import com.polaris.exam.dto.StatisticParam;
import com.polaris.exam.pojo.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-02-05
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    /**
     * 登陆情况统计
     * @return List<LoginLogStatistic>
     */
    List<StatisticParam> getLoginLogStatistic();
}
