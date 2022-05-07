package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessagePageRequest;
import com.polaris.exam.dto.message.MessageRequest;
import com.polaris.exam.mapper.MessageUserMapper;
import com.polaris.exam.pojo.Message;
import com.polaris.exam.mapper.MessageMapper;
import com.polaris.exam.pojo.MessageUser;
import com.polaris.exam.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.service.IMessageUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    private final MessageMapper messageMapper;
    private final MessageUserMapper messageUserMapper;
    private final IMessageUserService messageUserService;
    public MessageServiceImpl(MessageMapper messageMapper, MessageUserMapper messageUserMapper, IMessageUserService messageUserService) {
        this.messageMapper = messageMapper;
        this.messageUserMapper = messageUserMapper;
        this.messageUserService = messageUserService;
    }

    /**
     * 根据ids获取消息列表
     *
     * @param ids List<Integer>
     * @return List<Message>
     */
    @Override
    public List<Message> selectMessageByIds(List<Integer> ids) {
        return messageMapper.selectBatchIds(ids);
    }

    @Override
    public Page<Message> pageList(Page<Message> page, String send) {
        if(send.isEmpty()){
            return messageMapper.selectPage(page,new QueryWrapper<Message>());
        }
        return messageMapper.selectPage(page,new QueryWrapper<Message>());
    }

    @Override
    public void sendMessage(Message message, List<MessageUser> messageUsers) {
        messageMapper.insert(message);
        messageUsers.forEach(m->m.setMessageId(message.getId()));
        messageUserService.saveBatch(messageUsers);
    }

    @Override
    public void read(Integer id) {
        MessageUser messageUser = messageUserMapper.selectById(id);
        if(messageUser.getReaded()){
            return;
        }
        messageUser.setReaded(true);
        messageUser.setCreateTime(new Date());
        messageUserMapper.updateById(messageUser);
        Message message = messageMapper.selectById(messageUser.getMessageId());
        message.setReadCount(message.getReadCount()+1);
        updateById(message);
    }

    /**
     * 未读人数
     *
     * @param userId Integer
     * @return Integer
     */
    @Override
    public Integer unReadCount(Integer userId) {
        return messageUserMapper.selectCount(new QueryWrapper<MessageUser>().eq("readed",false));
    }

    /**
     * 消息详情
     *
     * @param id Integer
     * @return Message
     */
    @Override
    public Message messageDetail(Integer id) {
        MessageUser messageUser = messageUserMapper.selectById(id);
        return messageMapper.selectById(messageUser.getMessageId());
    }

    /**
     * 根据消息ids获取 用户消息列表
     *
     * @param ids List<Integer>
     * @return List<MessageUser>
     */
    @Override
    public List<MessageUser> selectByMessageIds(List<Integer> ids) {
        return messageUserService.selectByMessageIds(ids);
    }

    @Override
    public Page<Message> getReceiveMessagesByUserId(Integer userId, MessagePageRequest model) {
        Page<Message> objectPage = new Page<>(model.getPage(), model.getLimit());
        List<Integer> messageIds = messageMapper.getMessageIdsByReceiveUser(userId);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if(!model.getSendUserName().isEmpty()){
            queryWrapper.eq("send_user_name", model.getSendUserName());
        }
        queryWrapper.in("id",messageIds);
        return messageMapper.selectPage(objectPage,queryWrapper);
    }

    @Override
    public Page<Message> getSendHistoryPage(Integer userId, MessagePageRequest model) {
        Page<Message> objectPage = new Page<>(model.getPage(), model.getLimit());
        return messageMapper.selectPage(objectPage, new QueryWrapper<Message>().eq("send_user_id",userId));
    }

    @Override
    public Integer getMessageCountUnRead(Integer receiveId) {
        return messageMapper.getMessageCountUnRead(receiveId);
    }
}
