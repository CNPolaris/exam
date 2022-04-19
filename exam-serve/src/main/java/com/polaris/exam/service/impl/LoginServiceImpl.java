package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.mapper.UserMapper;
import com.polaris.exam.pojo.User;
import com.polaris.exam.security.util.JwtTokenUtil;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.RespBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final IUserService userService;
    private final UserDetailsService userDetailsService;
    private final AdminCacheService adminCacheService;
    private final ILoginLogService loginLogService;
    public LoginServiceImpl(PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, IUserService userService, UserDetailsService userDetailsService, AdminCacheService adminCacheService, ILoginLogService loginLogService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.adminCacheService = adminCacheService;
        this.loginLogService = loginLogService;
    }

    /**
     * 登陆之后返回token
     * @param username 用户名
     * @param password 密码
     * @param request 请求
     * @return RespBean
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {
        //登陆
        String token = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || ! passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        //用户是否禁用
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用,请联系管理员");
        }
        try {
            // 更新security登陆对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 生成Token
            token = jwtTokenUtil.generatorToken(userDetails);
            //更新最后登录时间
            User user = userService.getUserByUsername(username);
            user.setLastActiveTime(new Date());
            userService.updateById(user);
            loginLogService.insertLoginLog(username);
        } catch (Exception e){
            log.warn(e.getMessage());
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登陆成功", tokenMap);
    }
    /**
     * 刷新token的具体实现类
     * @param token 令牌
     * @return String
     */
    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }
}
