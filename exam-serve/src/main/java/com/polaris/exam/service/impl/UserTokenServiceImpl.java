package com.polaris.exam.service.impl;

import com.polaris.exam.pojo.UserToken;
import com.polaris.exam.mapper.UserTokenMapper;
import com.polaris.exam.service.IUserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户令牌表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {

}
