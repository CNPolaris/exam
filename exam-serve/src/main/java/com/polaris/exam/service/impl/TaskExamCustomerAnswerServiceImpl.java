package com.polaris.exam.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.polaris.exam.dto.task.TaskItemAnswerObject;
import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.TaskExamCustomerAnswer;
import com.polaris.exam.mapper.TaskExamCustomerAnswerMapper;
import com.polaris.exam.pojo.TextContent;
import com.polaris.exam.service.ITaskExamCustomerAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.service.ITextContentService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户任务表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class TaskExamCustomerAnswerServiceImpl extends ServiceImpl<TaskExamCustomerAnswerMapper, TaskExamCustomerAnswer> implements ITaskExamCustomerAnswerService {
    private final TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
    private final ITextContentService textContentService;

    public TaskExamCustomerAnswerServiceImpl(TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper, ITextContentService textContentService) {
        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
        this.textContentService = textContentService;
    }

    @Override
    public void insertOrUpdate(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer) {
        Integer taskId = examPaper.getTaskExamId();
        Integer userId = examPaperAnswer.getCreateUser();
        TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerMapper.selectOne(new QueryWrapper<TaskExamCustomerAnswer>().eq("task_exam_id",taskId).eq("create_user",userId));
        if (null == taskExamCustomerAnswer) {
            taskExamCustomerAnswer = new TaskExamCustomerAnswer();
            taskExamCustomerAnswer.setCreateTime(new Date());
            taskExamCustomerAnswer.setCreateUser(userId);
            taskExamCustomerAnswer.setTaskExamId(taskId);
            List<TaskItemAnswerObject> taskItemAnswerObjects = Arrays.asList(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            TextContent textContent = textContentService.jsonConvertInsert(taskItemAnswerObjects,null);
            textContentService.save(textContent);
            taskExamCustomerAnswer.setTextContentId(textContent.getId());
            save(taskExamCustomerAnswer);
        } else {
            TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
            List<TaskItemAnswerObject> taskItemAnswerObjects = JSONUtil.toList(textContent.getContent(), TaskItemAnswerObject.class);
            taskItemAnswerObjects.add(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
            textContentService.updateById(textContent);
        }
    }
}
