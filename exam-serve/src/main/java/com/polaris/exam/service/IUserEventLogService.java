package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.UserEventLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户日志表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IUserEventLogService extends IService<UserEventLog> {
    /**
     * 插入用户事件
     * @param userEventLog UserEventLog
     */
    void insertEventLog(UserEventLog userEventLog);

    /**
     * 获取用户操作日志
     * @param page Page<UserEventLog>
     * @param username String
     * @return Page<UserEventLog>
     */
    Page<UserEventLog> selectUserEventLog(Page<UserEventLog> page, String username);
}
