package com.polaris.exam.controller.teacher;

import com.polaris.exam.dto.dashboard.TeacherDashboard;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.IClassService;
import com.polaris.exam.service.IExamPaperService;
import com.polaris.exam.service.IQuestionService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "首页api", tags = "教师端首页模块")
@RestController("TeacherDashController")
@RequestMapping("/api/teacher/dash")
public class DashController {
    private final IClassService classService;
    private final IUserService userService;
    private final IExamPaperService examPaperService;
    private final IQuestionService questionService;
    public DashController(IClassService classService, IUserService userService, IExamPaperService examPaperService, IQuestionService questionService) {
        this.classService = classService;
        this.userService = userService;
        this.examPaperService = examPaperService;
        this.questionService = questionService;
    }

    @ApiOperation("教师管理的班级总数和总人数")
    @GetMapping("/info")
    public RespBean getDashAboutClass(Principal principal){
        User teacher = userService.getUserByUsername(principal.getName());
        List<Integer> classIds = classService.getClassIdsByTeacherId(teacher.getId());
        TeacherDashboard teacherDashboard = new TeacherDashboard();
        teacherDashboard.setClassCount(classIds.size());
        teacherDashboard.setStudentCount(classService.getStudentCountByClassIds(classIds));
        teacherDashboard.setExamPaperCount(examPaperService.getExamPaperCountByTeacherId(teacher.getId()));
        teacherDashboard.setQuestionCount(questionService.getQuestionCountByUser(teacher.getId()));
        teacherDashboard.setClassUserPie(classService.getClassUserPie(classIds));
        teacherDashboard.setClassPaperPie(classService.getClassPaperPie(classIds));
        return RespBean.success("成功",teacherDashboard);
    }
}
