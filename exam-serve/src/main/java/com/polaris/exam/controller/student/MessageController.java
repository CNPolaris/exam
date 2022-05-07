package com.polaris.exam.controller.student;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessageRequest;
import com.polaris.exam.dto.message.MessageResponse;
import com.polaris.exam.pojo.MessageUser;
import com.polaris.exam.service.IMessageService;
import com.polaris.exam.service.IMessageUserService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api("通用消息管理")
@RestController("StudentMessageController")
@RequestMapping("/api/message")
public class MessageController {
    private final IMessageService messageService;
    private final IUserService userService;
    private final IMessageUserService messageUserService;
    public MessageController(IMessageService messageService, IUserService userService, IMessageUserService messageUserService) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageUserService = messageUserService;
    }

    @ApiOperation("根据id获取未读消息")
    @GetMapping("/unread/count")
    public RespBean getMessageCountUnRead(Principal principal) {
        return RespBean.success(messageService.getMessageCountUnRead(userService.getUserByUsername(principal.getName()).getId()));
    }

    @ApiOperation("获取消息列表")
    @PostMapping("/list")
    public RespBean getMessageList(Principal principal, @RequestBody MessageRequest model){
        model.setReceiveUserId(userService.getUserByUsername(principal.getName()).getId());
        Page<MessageUser> messageUserPage = messageUserService.getMessageUserPage(model);
        Map<String, Object> response = new HashMap<>();
        response.put("total", messageUserPage.getTotal());
        ArrayList<MessageResponse> messageList = new ArrayList<>();
        messageUserPage.getRecords().forEach(messageUser -> {
            MessageResponse temp = new MessageResponse();
            BeanUtil.copyProperties(messageService.getById(messageUser.getMessageId()),temp);
            temp.setId(messageUser.getId());
            temp.setReaded(messageUser.getReaded());
            messageList.add(temp);
        });
        response.put("list", messageList);
        return RespBean.success(response);
    }

    @ApiOperation("标记消息已读")
    @GetMapping("/read/{id}")
    public RespBean readMessage(@PathVariable Integer id){
        messageUserService.readMessage(id);
        return RespBean.success("消息已读");
    }
}
