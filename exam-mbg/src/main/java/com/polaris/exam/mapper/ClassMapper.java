package com.polaris.exam.mapper;

import com.polaris.exam.pojo.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 班级表 Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
public interface ClassMapper extends BaseMapper<Class> {
    /**
     * 根据用户id获取所属班级信息
     * @param userId Integer
     * @return Class
     */
    @Select("SELECT * FROM class WHERE " +
            "id = ( SELECT class_id FROM class_user WHERE user_id = #{userId})")
    Class getClassByUserId(Integer userId);

    /**
     * 根据教师Id获取所属班级信息
     * @param tId Integer
     * @return List<Class>
     */
    @Select("SELECT * FROM class WHERE id IN(SELECT class_id FROM class_teacher WHERE teacher_id =#{tId})")
    List<Class> getClassByTeacherId(Integer tId);

    /**
     * 获取某个教师所属的班级ids
     * @param tId Integer
     * @return List<Integer>
     */
    @Select("SELECT  class_id FROM class_teacher WHERE teacher_id =#{tId}")
    List<Integer> getClassIdsByTeacherId(Integer tId);

    /**
     * 根据试卷id获取所属的班级ids
     * @param paperId Integer
     * @return List<Integer>
     */
    @Select("SELECT class_id FROM exam_class WHERE exam_id = #{paperId}")
    List<Integer> getClassIdsByExamId(Integer paperId);

    /**
     * 根据班级id统计学生人数
     * @param classId Integer
     * @return Integer
     */
    @Select("SELECT COUNT(user_id) FROM class_user WHERE class_id =#{classId}")
    Integer getStudentCountByClassId(Integer classId);

    /**
     * 根据班级id获取学生id
     * @param classId Integer
     * @return List<Integer>
     */
    @Select("SELECT user_id FROM class_user WHERE class_id = #{classId}")
    List<Integer> getStudentIdsByClassId(Integer classId);

    /**
     * 查询学生数量
     * @return Integer
     */
    @Select("SELECT COUNT(user_id) FROM class_user WHERE `status`=1")
    Integer getStudentCount();

    /**
     * 获取教师教授的一个学科id
     * @param classId Integer
     * @return Integer
     */
    Integer getOneTeacherSubject(Integer classId);

    /**
     * 获取教师教授的一个班级
     * @param teacherId Integer
     * @return Class
     */
    Class getTeacherOneClass(Integer teacherId);

    /**
     * 获取教师管理的学科ids
     * @param teacherId Integer
     * @return List<Integer>
     */
    @Select("SELECT subject_id FROM class_teacher WHERE teacher_id = #{teacherId} AND `status`=1")
    List<Integer> getTeacherSubjectIds(Integer teacherId);
}
