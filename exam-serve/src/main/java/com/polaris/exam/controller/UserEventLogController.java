package com.polaris.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.UserEventLog;
import com.polaris.exam.service.IUserEventLogService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 * 用户日志表 前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Api(tags = "用户操作日志")
@RestController
@RequestMapping("/api/user/event/log")
public class UserEventLogController {
    private final IUserEventLogService userEventLogService;


    public UserEventLogController(IUserEventLogService userEventLogService) {
        this.userEventLogService = userEventLogService;
    }

    @ApiOperation(value = "获取用户操作日志")
    @GetMapping("/list")
    public RespBean selectUserEventLog(@RequestParam(defaultValue = "1")Integer page, @RequestParam(defaultValue = "15")Integer limit,@RequestParam(required = false)String username){
        Page<UserEventLog> userEventLogPage = userEventLogService.selectUserEventLog(new Page<UserEventLog>(page, limit), username);
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", userEventLogPage.getRecords());
        response.put("total", userEventLogPage.getTotal());
        return RespBean.success("成功",response);
    }
}
