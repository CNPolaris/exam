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
}
