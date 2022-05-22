package com.polaris.exam.controller.teacher;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.message.MessagePageRequest;
import com.polaris.exam.dto.message.MessageSend;
import com.polaris.exam.pojo.Message;
import com.polaris.exam.pojo.MessageUser;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IMessageService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "消息管理", tags = "教师端消息管理模块")
@RestController("TeacherMessageController")
@RequestMapping("/api/teacher/message")
public class MessageController {
    private final IMessageService messageService;
    private final IUserService userService;

    public MessageController(IMessageService messageService, IUserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @ApiOperation(value = "消息发送记录")
    @PostMapping("/send/history")
    public RespBean getAllReceiveMessage(Principal principal, @RequestBody MessagePageRequest model){
        if(model==null){
            return RespBean.error("内容不能为空");
        }
//        Page<Message> messagePage = messageService.getReceiveMessagesByUserId(userService.getUserByUsername(principal.getName()).getId(), model);
//        HashMap<String, Object> re = new HashMap<>(2);
//        re.put("total", messagePage.getTotal());
//        ArrayList<MessageResponse> messageList = new ArrayList<>();
//
//        List<Integer> ids = messagePage.getRecords().stream().map(d -> d.getId()).collect(Collectors.toList());
//        List<MessageUser> messageUsers = ids.size()==0?null:messageService.selectByMessageIds(ids);
//
//        messagePage.getRecords().forEach(m -> {
//            MessageResponse messageResponse = BeanUtil.copyProperties(m, MessageResponse.class);
//            String receives = messageUsers.stream().filter(d -> d.getMessageId().equals(m.getId())).map(d -> d.getReceiveUserName())
//                    .collect(Collectors.joining(","));
//            messageResponse.setReceives(receives);
//            messageResponse.setCreateTime(DateUtil.formatTime(m.getCreateTime()));
//            messageList.add(messageResponse);
//        });
//        re.put("list", messageList);
        Page<Message> sendHistoryPage = messageService.getSendHistoryPage(userService.getUserByUsername(principal.getName()).getId(), model);
        HashMap<String, Object> re = new HashMap<>(2);
        re.put("total",sendHistoryPage.getTotal());
        re.put("list",sendHistoryPage.getRecords());
        return RespBean.success("获取收到消息成功", re);
    }
    @ApiOperation(value = "教师发送消息")
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
