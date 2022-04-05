package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author polaris
 * @since 2022-02-05
 */
public interface ILoginLogService extends IService<LoginLog> {
    /**
     * 添加登录日志
     * @param username String
     */
    void insertLoginLog(String username);

    /**
     * 查询登录日志
     * @param page Page<LoginLog>
     * @param username String
     * @return Page<LoginLog>
     */
    Page<LoginLog> selectLoginLog(Page<LoginLog> page, String username);

    /**
     * 查询用户最近五次的登录日志
     * @param username String
     * @return List<LoginLog>
     */
    List<LoginLog> selectUserLastLog(String username);
}
