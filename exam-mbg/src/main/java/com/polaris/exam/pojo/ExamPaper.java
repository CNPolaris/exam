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
 * 试卷表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_paper")
@ApiModel(value="ExamPaper对象", description="试卷表")
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "试卷名称")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String name;

    @ApiModelProperty(value = "学科id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer subjectId;

    @ApiModelProperty(value = "试卷类型(1.固定试卷 2.时段试卷 3.任务试卷)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer paperType;

    @ApiModelProperty(value = "年级(1-12)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer gradeLevel;

    @ApiModelProperty(value = "试卷总分(百分制)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer score;

    @ApiModelProperty(value = "题目数量")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionCount;

    @ApiModelProperty(value = "建议时长(分钟)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer suggestTime;

    @ApiModelProperty(value = "时段试卷 开始时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date limitStartTime;

    @ApiModelProperty(value = "时段试卷 结束时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date limitEndTime;

    @ApiModelProperty(value = "试卷框架 ")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer frameTextContentId;

    @ApiModelProperty(value = "创建者")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "任务试卷id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer taskExamId;

    @ApiModelProperty(value = "状态（0.启用 1.终止）")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;


}
