package com.polaris.exam.controller.admin;

import com.polaris.exam.dto.dashboard.AdminDashboard;
import com.polaris.exam.service.IClassService;
import com.polaris.exam.service.IExamPaperService;
import com.polaris.exam.service.IQuestionService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author CNPolaris
 * @version 1.0
 */

@Api(value = "管理员首页" ,tags="管理员端")
@RestController("AdminDashboardController")
@RequestMapping("/api/admin/dash")
public class DashboardController {
    private final IQuestionService questionService;
    private final IExamPaperService examPaperService;
    private final IClassService classService;
    public DashboardController(IQuestionService questionService, IExamPaperService examPaperService, IClassService classService) {
        this.questionService = questionService;
        this.examPaperService = examPaperService;
        this.classService = classService;
    }


    @ApiOperation("基础数据统计")
    @GetMapping("/base/count")
    public RespBean getBaseCount(Principal principal){
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setStudentCount(classService.getStudentCount());
        dashboard.setClassCount(classService.getClassCount());
        dashboard.setPaperCount(examPaperService.getPaperCount());
        dashboard.setQuestionCount(questionService.selectQuestionCount());
        return RespBean.success("管理员端首页信息获取成功",dashboard);
    }
}
