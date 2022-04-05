package com.polaris.exam.service;

import java.util.Map;

/**
 * @date 2022/1/22
 * @author CNPolaris
 * @version 1.0
 **/

public interface MonitorService {
    /**
     * 查询系统信息
     * @return Map<String,Object>
     */
    Map<String,Object> getServeInfo();
}
