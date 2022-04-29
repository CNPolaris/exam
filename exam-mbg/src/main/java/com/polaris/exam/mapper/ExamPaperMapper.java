package com.polaris.exam.mapper;

import com.polaris.exam.pojo.ExamPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 试卷表 Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {
    /**
     * 根据用户Id获取所属的试卷
     * @param userId Integer
     * @return List<ExamPaper>
     */
    @Select("SELECT * FROM exam_paper WHERE id IN(SELECT exam_id \n" +
            "FROM exam_class " +
            "WHERE class_id = (SELECT class_id FROM class_user WHERE user_id = #{userId})) AND `status` = 1 LIMIT 10")
    List<ExamPaper> getUserPaper(Integer userId);

    /**
     * 查询用户所属的任务试卷
     * @param userId Integer
     * @param type Integer
     * @return List<ExamPaper>
     */
    @Select("SELECT * FROM exam_paper WHERE id IN(SELECT exam_id " +
            "FROM exam_class " +
            "WHERE class_id = (SELECT class_id " +
            "FROM class_user " +
            "WHERE user_id = #{userId})) AND `status` =1 AND `paper_type` = #{type} LIMIT 10")
    List<ExamPaper> getTaskPaper(Integer userId, Integer type);

    /**
     * 根据班级id统计试卷数量
     * @param classId Integer
     * @return Integer
     */
    @Select("SELECT COUNT(exam_id) FROM exam_class WHERE class_id = #{classId}")
    Integer getExamPaperCount(Integer classId);
    @Select("SELECT exam_id FROM exam_class WHERE class_id = #{classId}")
    List<Integer> getPaperIdsByClassId(Integer classId);

    /**
     * 根据试卷id获取班级ids
     * @param paperId Integer
     * @return List<Integer>
     */
    @Select("SELECT class_id FROM exam_class WHERE exam_id = #{paperId}")
    List<Integer> getClassIdsByPaperId(Integer paperId);

    /**
     * 根据学生id获取班级试卷ids
     * @param id 学生id
     * @return List<Integer> 试卷ids
     */
    List<Integer> getPaperIdsToStudent(Integer id);
}
