package com.polaris.exam.service;

import com.polaris.exam.pojo.ClassTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 班级-教师关联表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
public interface IClassTeacherService extends IService<ClassTeacher> {
    /**
     * 通过教师id查询所属班级id
     * @param id Integer
     * @return Integer
     */
    Integer getClassIdByTeacher(Integer id);
}
