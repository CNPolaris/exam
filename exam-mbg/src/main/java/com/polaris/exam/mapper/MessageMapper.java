package com.polaris.exam.mapper;

import com.polaris.exam.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 根据用户id获取发送给该用户的全部消息ids
     * @param userId Integer
     * @return List<Integer>
     */
    @Select("SELECT message_id FROM message_user WHERE receive_user_id = #{userId}")
    List<Integer> getMessageIdsByReceiveUser(Integer userId);
}
