package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.StatisticParam;
import com.polaris.exam.pojo.LoginLog;
import com.polaris.exam.mapper.LoginLogMapper;
import com.polaris.exam.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-02-05
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    private final LoginLogMapper loginLogMapper;

    public LoginLogServiceImpl(LoginLogMapper loginLogMapper) {
        this.loginLogMapper = loginLogMapper;
    }

    /**
     * 添加登录日志
     *
     * @param username String
     */
    @Override
    public void insertLoginLog(String username) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(username);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String ip = StringUtils.getIp(request);
        loginLog.setIp(ip);
        loginLog.setAddress(StringUtils.getCityInfo(ip));
        loginLog.setBrowser(StringUtils.getBrowser(request));
        loginLog.setLoginTime(new Date());
        save(loginLog);
    }

    /**
     * 查询登录日志
     *
     * @param page     Page<LoginLog>
     * @param username String
     * @return Page<LoginLog>
     */
    @Override
    public Page<LoginLog> selectLoginLog(Page<LoginLog> page, String username) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        if(username.isEmpty()){
            return loginLogMapper.selectPage(page, queryWrapper);
        } else {
            queryWrapper.eq("user_name", username);
        }
        return loginLogMapper.selectPage(page,queryWrapper);
    }

    /**
     * 查询用户最近五次的登录日志
     *
     * @param username String
     * @return List<LoginLog>
     */
    @Override
    public List<LoginLog> selectUserLastLog(String username) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username).orderByDesc("id");
        return loginLogMapper.selectList(queryWrapper).subList(0,4);
    }

    @Override
    public List<StatisticParam> getLoginLogStatistic() {
        return loginLogMapper.getLoginLogStatistic();
    }
}
