package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessagePageRequest;
import com.polaris.exam.dto.message.MessageRequest;
import com.polaris.exam.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.pojo.MessageUser;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IMessageService extends IService<Message> {
    /**
     * 根据ids获取消息列表
     * @param ids List<Integer>
     * @return List<Message>
     */
    List<Message> selectMessageByIds(List<Integer> ids);

    /**
     * 分页
     * @param page Page<Message>
     * @param model MessagePageRequest
     * @return Page<Message>
     */
    Page<Message> pageList(Page<Message> page, MessagePageRequest model);

    /**
     * 发送消息
     * @param message Message
     * @param messageUsers List<MessageUser>
     */
    void sendMessage(Message message,List<MessageUser> messageUsers);

    /**
     * 阅读
     * @param id Integer
     */
    void read(Integer id);

    /**
     * 未读人数
     * @param userId Integer
     * @return Integer
     */
    Integer unReadCount(Integer userId);

    /**
     * 消息详情
     * @param id Integer
     * @return Message
     */
    Message messageDetail(Integer id);

    /**
     * 根据消息ids获取 用户消息列表
     * @param ids List<Integer>
     * @return List<MessageUser>
     */
    List<MessageUser> selectByMessageIds(List<Integer> ids);

    /**
     * 根据用户id获取收到的所有消息
     * @param userId Integer
     * @param model MessagePageRequest
     * @return List<Message>
     */
    Page<Message> getReceiveMessagesByUserId(Integer userId, MessagePageRequest model);

    /**
     * 消息发送记录
     * @param userId Integer
     * @param model MessagePageRequest
     * @return Page<Message>
     */
    Page<Message> getSendHistoryPage(Integer userId, MessagePageRequest model);

    /**
     * 根据用户id获取未读消息数量
     * @param receiveId Integer
     * @return Integer
     */
    Integer getMessageCountUnRead(Integer receiveId);
}
