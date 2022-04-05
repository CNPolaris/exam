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
 * 试卷答案表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_paper_answer")
@ApiModel(value="ExamPaperAnswer对象", description="试卷答案表")
public class ExamPaperAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "试卷id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer examPaperId;

    @ApiModelProperty(value = "试卷名称")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String paperName;

    @ApiModelProperty(value = "试卷类型(1.固定试卷 2.时段试卷 3.任务试卷)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer paperType;

    @ApiModelProperty(value = "学科id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer subjectId;

    @ApiModelProperty(value = "系统判定得分")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer systemScore;

    @ApiModelProperty(value = "最终得分")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer userScore;

    @ApiModelProperty(value = "试卷总分")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer paperScore;

    @ApiModelProperty(value = "做对题目数量")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionCorrect;

    @ApiModelProperty(value = "题目总数量")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionCount;

    @ApiModelProperty(value = "做题时间(秒)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer doTime;

    @ApiModelProperty(value = "试卷状态(0.待判分 1.完成)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;

    @ApiModelProperty(value = "答题学生")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer createUser;

    @ApiModelProperty(value = "提交时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "任务试卷id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer taskExamId;


}
