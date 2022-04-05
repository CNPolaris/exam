package com.polaris.exam.service;

import com.polaris.exam.pojo.ExamClass;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 试卷-班级关联表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
public interface IExamClassService extends IService<ExamClass> {
    /**
     * 添加试卷-班级关联
     * @param examId Integer
     * @param classIds List<Integer>
     */
    void createExamClassRelation(Integer examId, List<Integer> classIds);

    /**
     * 获取班级id列表
     * @param examId Integer
     * @return List<Integer>
     */
    List<Integer> getClassIdByExamId(Integer examId);

    /**
     * 删除试卷时清除和班级的联系
     * @param examId Integer
     */
    void clearRelationByExamId(Integer examId);
    /**
     * 删除试卷时清除和班级的联系
     * @param classId Integer
     */
    void clearRelationByClassId(Integer classId);

}
