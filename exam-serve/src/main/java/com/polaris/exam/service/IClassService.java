package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.classes.ClassRequest;
import com.polaris.exam.pojo.Class;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
    int getClassCount();

    /**
     * 获取学生数量
     * @return Integer
     */
    Integer getStudentCount();
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
     * 根据教师id获取所属班级ids
     * @param tId Integer
     * @return List<Integer>
     */
    List<Integer> getClassIdsByTeacherId(Integer tId);
    /**
     * 根据教师Id获取所属班级信息
     * @param page Page<Class>
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

    /**
     * 根据班级ids获取对应的学生人数
     * @param classIds List<Integer>
     * @return int
     */
    Integer getStudentCountByClassIds(List<Integer> classIds);

    /**
     * 统计各班级人数
     * @param classIds List<Integer> 班级ids
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getClassUserPie(List<Integer> classIds);

    /**
     * 统计各班级试卷分布
     * @param classIds List<Integer>
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> getClassPaperPie(List<Integer> classIds);

    /**
     * 根据试卷id获取对应的班级列表
     * @param paperId Integer
     * @return List<Class>
     */
    List<Class> getClassListByPaperId(Integer paperId);

    /**
     * 根据班级id获取学生ids
     * @param classId Integer
     * @return List<Integer>
     */
    List<Integer> getStudentIdsByClassId(Integer classId);
    /**
     * 获取教师教授的一个班级
     * @param teacherId Integer
     * @return Class
     */
    Class getTeacherOneClass(Integer teacherId);
    /**
     * 获取教师教授的一个学科id
     * @param classId Integer
     * @return Integer
     */
    Integer getOneTeacherSubject(Integer classId);

    /**
     * 获取教师管理的学科ids
     * @param teacherId Integer
     * @return List<Integer>
     */
    List<Integer> getTeacherSubjectIds(Integer teacherId);
}
