package com.polaris.exam.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 班级-教师关联表
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("class_teacher")
@ApiModel(value="ClassTeacher对象", description="班级-教师关联表")
public class ClassTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "班级表")
    private Integer classId;

    @ApiModelProperty(value = "教师表")
    private Integer teacherId;

    private Integer subjectId;

    @ApiModelProperty(value = "状态")
    private Integer status;


}
