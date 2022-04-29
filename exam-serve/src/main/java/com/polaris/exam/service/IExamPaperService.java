package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.paper.ExamPageParam;
import com.polaris.exam.dto.paper.ExamPaperEditRequest;
import com.polaris.exam.dto.paper.ExamPaperStudentPageRequest;
import com.polaris.exam.pojo.ExamPaper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.pojo.User;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface IExamPaperService extends IService<ExamPaper> {
    /**
     * 试卷列表
     * @param page page
     * @param param param
     * @return Page<ExamPaper>
     */
    Page<ExamPaper> examPaperPage(Page<ExamPaper> page, ExamPageParam param);

    /**
     * 获取试卷列表于成绩分析
     * @param userId
     * @param model
     * @return
     */
    Page<ExamPaper> getResultPage(Integer userId, ExamPageParam model);

    /**
     * 获取试卷数量
     * @return int
     */
    int selectCount();

    /**
     * 统计教师创建的试卷数量
     * @param userId Integer
     * @return Integer
     */
    Integer getExamPaperCountByTeacherId(Integer userId);
    /**
     * 获取创建者所属的试卷数量
     * @param userId Integer
     * @return int
     */
    int selectCountByUser(Integer userId);
    /**
     * 用户自己创建的试卷
     * @param page page
     * @param subjectId subjectId
     * @param userId userId
     * @return Page<ExamPaper>
     */
    Page<ExamPaper> myExamPaperPage(Page<ExamPaper> page,Integer userId, Integer subjectId);

    /**
     * 通过id选中试卷
     * @param examId examId
     * @return ExamPaper
     */
    ExamPaper selectExamPaper(Integer examId);
    /**
     * 添加试卷
     * @param examPaperEditRequest examPaperEditRequest
     * @param user user
     * @return ExamPaper
     */
    ExamPaper saveExamPaper(ExamPaperEditRequest examPaperEditRequest, User user);

    /**
     * 更新试卷
     * @param examPaperEditRequest examPaperEditRequest
     * @return ExamPaper
     */
    ExamPaper updateExamPaper(ExamPaperEditRequest examPaperEditRequest);
    /**
     * 删除试卷
     * 实际是修改状态,避免测试麻烦
     * @param id id
     * @return ExamPaper
     */
    ExamPaper deleteExamPaper(Integer id);
    /**
     * 试卷映射
     * @param id id
     * @return ExamPaperEditRequest
     */
    ExamPaperEditRequest examPaperToModel(Integer id);

    /**
     * 更新试卷状态
     * @param id Integer
     * @param status Integer
     * @return ExamPaper
     */
    ExamPaper updateStatus(Integer id, Integer status);
    /**
     * 根据用户Id获取所属的试卷
     * @param userId Integer
     * @return List<ExamPaper>
     */
    List<ExamPaper> getUserPaper(Integer userId);

    /**
     * 根据班级id获取所有的试卷列表
     * @param classId Integer
     * @return List<ExamPaper>
     */
    List<ExamPaper> getPaperByClassId(Integer classId);
    /**
     * 获取任务试卷
     * @param userId Integer
     * @param type Integer
     * @return List<ExamPaper>
     */
    List<ExamPaper> getTaskPaper(Integer userId, Integer type);

    /**
     * 学生端获取试卷中心
     * @param id 学生id
     * @param model ExamPaperStudentPageRequest
     * @return Page<ExamPaper>
     */
    Page<ExamPaper> getStudentPage(Integer id,ExamPaperStudentPageRequest model);
}
