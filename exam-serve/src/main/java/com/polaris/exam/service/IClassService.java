package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.classes.ClassRequest;
import com.polaris.exam.pojo.Class;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
public interface IClassService extends IService<Class> {
    /**
     * 获取班级总数
     * @return int
     */
    int selectClassCount();

    /**
     * 获取班级列表
     * @param page Page<Class>
     * @param name String
     * @return Page<Class>
     */
    Page<Class> classPage(Page<Class> page,String name);

    /**
     * 通过id查找
     * @param classId Integer
     * @return Class
     */
    Class selectById(Integer classId);

    /**
     * 通过id删除
     * @param classId Integer
     */
    void deleteById(Integer classId);

    /**
     * 增加学生人数
     * @param classId Integer
     */
    void increaseStudentCount(Integer classId);
    /**
     * 减少学生人数
     * @param classId Integer
     */
    void decreaseStudentCount(Integer classId);

    /**
     * 更新或创建clas
     * @param model ClassRequest
     * @return Class
     */
    Class editClass(ClassRequest model);

    /**
     * 根据用户id查询所属班级信息
     * @param userId Integer
     * @return Class
     */
    Class getClassByUserId(Integer userId);
    /**
     * 根据教师Id获取所属班级信息
     * @param tId Integer
     * @return List<Class>
     */
    List<Class> getClassByTeacherId(Integer tId);

    /**
     * 根据教师Id获取所属班级信息
     * @param tId Integer
     * @return Page<Class>
     */
    Page<Class> getClassByTeacherId(Page<Class> page, Integer tId);

    /**
     * 根据试卷id获取班级ids
     * @param paperId Integer
     * @return List<Integer>
     */
    List<Integer> getClassIdsByExamId(Integer paperId);
}
