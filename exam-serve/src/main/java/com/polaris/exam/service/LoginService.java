package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.pojo.User;
import com.polaris.exam.utils.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CNPolaris
 * @version 1.0
 */
public interface LoginService extends IService<User> {
    /**
     * 刷新token
     * @Description refreshToken 刷新用户token
     * @date 2021/5/14
     * @param token 令牌
     * @return java.lang.String
     **/
    String refreshToken(String token);
    /**
     * 登陆之后返回token
     * @param username 用户名
     * @param password 密码
     * @param request 请求
     * @return RespBean
     */
    RespBean login(String username, String password, HttpServletRequest request);
}
