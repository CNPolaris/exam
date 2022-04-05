package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.ExamClass;
import com.polaris.exam.mapper.ExamClassMapper;
import com.polaris.exam.service.IExamClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 试卷-班级关联表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Service
public class ExamClassServiceImpl extends ServiceImpl<ExamClassMapper, ExamClass> implements IExamClassService {

    private final ExamClassMapper examClassMapper;

    public ExamClassServiceImpl(ExamClassMapper examClassMapper) {
        this.examClassMapper = examClassMapper;
    }
    /**
     * 添加试卷-班级关联
     * @param examId Integer
     * @param classIds List<Integer>
     */
    @Override
    public void createExamClassRelation(Integer examId, List<Integer> classIds) {
        ArrayList<ExamClass> arrayList = new ArrayList<>();
        for(Integer classId:classIds){
            ExamClass examClass = new ExamClass();
            examClass.setExamId(examId);
            examClass.setClassId(classId);
            examClass.setStatus(StatusEnum.OK.getCode());
            arrayList.add(examClass);
        }
        saveBatch(arrayList);
    }

    /**
     * 通过试卷id获取班级列表
     * @param examId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> getClassIdByExamId(Integer examId) {
        QueryWrapper<ExamClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("class_id").eq("exam_id",examId);
        List<ExamClass> examClasses = examClassMapper.selectList(queryWrapper);
        List<Integer> classIds = new ArrayList<>();
        for(ExamClass e:examClasses){
            classIds.add(e.getClassId());
        }
        return classIds;
    }

    @Override
    public void clearRelationByExamId(Integer examId) {
        QueryWrapper<ExamClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id",examId);
        examClassMapper.delete(queryWrapper);
    }

    @Override
    public void clearRelationByClassId(Integer classId) {
        QueryWrapper<ExamClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        examClassMapper.delete(queryWrapper);
    }
}
