package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessageRequest;
import com.polaris.exam.pojo.MessageUser;
import com.polaris.exam.mapper.MessageUserMapper;
import com.polaris.exam.service.IMessageUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class MessageUserServiceImpl extends ServiceImpl<MessageUserMapper, MessageUser> implements IMessageUserService {
    private final MessageUserMapper messageUserMapper;

    public MessageUserServiceImpl(MessageUserMapper messageUserMapper) {
        this.messageUserMapper = messageUserMapper;
    }

    /**
     * 通过messageId获取消息用户关联
     *
     * @param ids List<Integer>
     * @return List<MessageUser>
     */
    @Override
    public List<MessageUser> selectByMessageIds(List<Integer> ids) {
        List<MessageUser> messageUserList = new ArrayList<>();
        for(Integer id:ids){
            messageUserList.add(messageUserMapper.selectOne(new QueryWrapper<MessageUser>().eq("message_id",id)));
        }
        return messageUserList;
    }

    @Override
    public Page<MessageUser> getMessageUserPage(MessageRequest model) {
        Page<MessageUser> page = new Page<>(model.getPage(), model.getLimit());
        QueryWrapper<MessageUser> queryWrapper = new QueryWrapper<>();
        return messageUserMapper.selectPage(page, queryWrapper.eq("receive_user_id",model.getReceiveUserId()));
    }

    @Override
    public void readMessage(Integer id) {
        MessageUser messageUser = getById(id);
        messageUser.setReaded(true);
        updateById(messageUser);
    }
}
