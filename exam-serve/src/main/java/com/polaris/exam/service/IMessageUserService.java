package com.polaris.exam.service;

import com.polaris.exam.pojo.MessageUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户消息表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IMessageUserService extends IService<MessageUser> {
    /**
     * 通过messageId获取消息用户关联
     * @param ids List<Integer>
     * @return List<MessageUser>
     */
    List<MessageUser> selectByMessageIds(List<Integer> ids);
}
