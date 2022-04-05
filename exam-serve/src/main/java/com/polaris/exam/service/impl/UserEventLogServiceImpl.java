package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.UserEventLog;
import com.polaris.exam.mapper.UserEventLogMapper;
import com.polaris.exam.service.IUserEventLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户日志表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class UserEventLogServiceImpl extends ServiceImpl<UserEventLogMapper, UserEventLog> implements IUserEventLogService {

    private final UserEventLogMapper userEventLogMapper;
    @Autowired
    public UserEventLogServiceImpl(UserEventLogMapper userEventLogMapper) {
        this.userEventLogMapper = userEventLogMapper;
    }

    @Override
    public void insertEventLog(UserEventLog userEventLog) {
        save(userEventLog);
    }

    /**
     * 获取用户操作日志
     *
     * @param page     Page<UserEventLog>
     * @param username String
     * @return Page<UserEventLog>
     */
    @Override
    public Page<UserEventLog> selectUserEventLog(Page<UserEventLog> page, String username) {
        QueryWrapper<UserEventLog> queryWrapper = new QueryWrapper<>();
        if (!username.isEmpty()) {
            queryWrapper.eq("user_name", username);
        }
        return userEventLogMapper.selectPage(page, queryWrapper);
    }
}
