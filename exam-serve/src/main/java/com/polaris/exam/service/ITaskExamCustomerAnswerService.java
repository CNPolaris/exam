package com.polaris.exam.service;

import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.TaskExamCustomerAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户任务表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface ITaskExamCustomerAnswerService extends IService<TaskExamCustomerAnswer> {
    /**
     * 插入或更新任务试卷答案表
     * @param examPaper ExamPaper
     * @param examPaperAnswer ExamPaperAnswer
     */
    void insertOrUpdate(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer);
}
