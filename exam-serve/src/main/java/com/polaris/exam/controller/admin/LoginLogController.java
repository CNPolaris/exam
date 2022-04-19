package com.polaris.exam.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.LoginLog;
import com.polaris.exam.service.ILoginLogService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-02-05
 */
@Api(tags = "登录日志")
@RestController("AdminLogController")
@RequestMapping("/api/admin/log")
public class LoginLogController {
    private final ILoginLogService loginLogService;

    public LoginLogController(ILoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @ApiOperation(value = "查询登录日志")
    @GetMapping("/login/list")
    public RespBean getLoginLogList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "15") Integer limit, @RequestParam(required = false) String username){
        Page<LoginLog> objectPage = new Page<>(page, limit);
        Map<String, Object> response = new HashMap<>();
        Page<LoginLog> loginLogPage = loginLogService.selectLoginLog(objectPage, username);
        response.put("total", loginLogPage.getTotal());
        response.put("data", loginLogPage.getRecords());
        return RespBean.success("登录日志",response);
    }

    @ApiOperation(value = "查询登录日志")
    @GetMapping("/login/last")
    public RespBean getLoginLogList(Principal principal){
        return RespBean.success("登录日志",loginLogService.selectUserLastLog(principal.getName()));
    }

}
