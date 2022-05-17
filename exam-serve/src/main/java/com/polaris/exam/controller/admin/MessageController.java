package com.polaris.exam.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessagePageRequest;
import com.polaris.exam.dto.message.MessageResponse;
import com.polaris.exam.dto.message.MessageSend;
import com.polaris.exam.pojo.Message;
import com.polaris.exam.pojo.MessageUser;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IMessageService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Api(value = "消息管理模块")
@RestController("AdminMessageController")
@RequestMapping("/api/admin/message")
public class MessageController {
    private final IMessageService messageService;
    private final IUserService userService;
    public MessageController(IMessageService messageService, IUserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @ApiOperation(value = "管理员消息列表")
    @PostMapping("/admin/list")
    public RespBean pageList(@RequestBody MessagePageRequest model){
        Page<Message> objectPage = new Page<>(model.getPage(), model.getLimit());
        Page<Message> resultPage = messageService.pageList(objectPage,model);
        Map<String, Object> result = new HashMap<>();

        List<Integer> ids = resultPage.getRecords().stream().map(d -> d.getId()).collect(Collectors.toList());
        List<MessageUser> messageUsers = ids.size()==0?null:messageService.selectByMessageIds(ids);
        List<MessageResponse> messageResponses = new ArrayList<>();
        List<Message> records = resultPage.getRecords();
        records.forEach(m->{
            MessageResponse messageResponse = new MessageResponse();
            BeanUtil.copyProperties(m,messageResponse);
            String receives = messageUsers.stream().filter(d -> d.getMessageId().equals(m.getId())).map(d -> d.getReceiveUserName())
                    .collect(Collectors.joining(","));
            messageResponse.setReceives(receives);
            messageResponse.setCreateTime(m.getCreateTime().toString());
            messageResponses.add(messageResponse);
        });

        result.put("total",resultPage.getTotal());
        result.put("data",messageResponses);

        return RespBean.success("成功",result);
    }

    @ApiOperation(value = "发送全体消息")
    @PostMapping("/send")
    public RespBean send(Principal principal, @RequestBody @Valid MessageSend model){
        User user = userService.getUserByUsername(principal.getName());
        List<User> receiveUser = userService.selectByIds(model.getReceiveUserIds());
        Date now = new Date();
        Message message = new Message();
        message.setTitle(model.getTitle());
        message.setContent(model.getContent());
        message.setCreateTime(now);
        message.setReadCount(0);
        message.setReceiveUserCount(receiveUser.size());
        message.setSendUserId(user.getId());
        message.setSendUserName(user.getUserName());
        message.setSendUserRealName(user.getRealName());

        List<MessageUser> messageUsers = receiveUser.stream().map(d->{
            MessageUser messageUser = new MessageUser();
            messageUser.setCreateTime(now);
            messageUser.setReaded(false);
            messageUser.setReceiveUserRealName(d.getRealName());
            messageUser.setReceiveUserId(d.getId());
            messageUser.setReceiveUserName(d.getUserName());
            return messageUser;
        }).collect(Collectors.toList());
        messageService.sendMessage(message,messageUsers);
        return RespBean.success("发送成功");
    }
}
