package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.polaris.exam.pojo.ClassTeacher;
import com.polaris.exam.mapper.ClassTeacherMapper;
import com.polaris.exam.service.IClassTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级-教师关联表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Service
public class
ClassTeacherServiceImpl extends ServiceImpl<ClassTeacherMapper, ClassTeacher> implements IClassTeacherService {

    private final ClassTeacherMapper classTeacherMapper;

    public ClassTeacherServiceImpl(ClassTeacherMapper classTeacherMapper) {
        this.classTeacherMapper = classTeacherMapper;
    }

    /**
     * 通过教师id查询所属班级id
     *
     * @param id Integer
     * @return Integer
     */
    @Override
    public Integer getClassIdByTeacher(Integer id) {
        return classTeacherMapper.selectOne(new QueryWrapper<ClassTeacher>().eq("teacher_id",id)).getClassId();
    }
}
