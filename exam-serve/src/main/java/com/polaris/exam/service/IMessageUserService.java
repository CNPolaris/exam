package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessageRequest;
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

    /**
     * 获取消息用户
     * @param model MessageRequest
     * @return Page<MessageUser>
     */
    Page<MessageUser> getMessageUserPage(MessageRequest model);

    /**
     * 标记消息已读
     * @param id Integer
     */
    void readMessage(Integer id);
}
