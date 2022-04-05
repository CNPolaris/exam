package com.polaris.exam.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("task_exam")
@ApiModel(value="TaskExam对象", description="任务表")
public class TaskExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String title;

    @ApiModelProperty(value = "年级")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer gradeLevel;

    @ApiModelProperty(value = "任务框架id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer frameTextContentId;

    @ApiModelProperty(value = "创建人")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "状态(0.有效 1.无效)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;

    @ApiModelProperty(value = "创建人用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String createUserName;


}
