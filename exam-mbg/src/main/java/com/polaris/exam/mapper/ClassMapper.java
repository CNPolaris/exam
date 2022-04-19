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
    List<Integer> getClassIds(Integer tId);

    /**
     * 根据试卷id获取所属的班级ids
     * @param paperId Integer
     * @return List<Integer>
     */
    @Select("SELECT class_id FROM exam_class WHERE exam_id = #{paperId}")
    List<Integer> getClassIdsByExamId(Integer paperId);
}
