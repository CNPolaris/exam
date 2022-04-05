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
 * 试卷题目答案表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_paper_question_customer_answer")
@ApiModel(value="ExamPaperQuestionCustomerAnswer对象", description="试卷题目答案表")
public class ExamPaperQuestionCustomerAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionId;

    @ApiModelProperty(value = "试卷id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer examPaperId;

    @ApiModelProperty(value = "答卷id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer examPaperAnswerId;

    @ApiModelProperty(value = "问题类型")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionType;

    @ApiModelProperty(value = "学科id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer subjectId;

    @ApiModelProperty(value = "得分")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer customerScore;

    @ApiModelProperty(value = "题目原始分数")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionScore;

    @ApiModelProperty(value = "问题内容")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionTextContentId;

    @ApiModelProperty(value = "作题答案")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String answer;

    @ApiModelProperty(value = "做题内容")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer textContentId;

    @ApiModelProperty(value = "是否正确")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Boolean doRight;

    @ApiModelProperty(value = "做题人")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer createUser;

    @ApiModelProperty(value = "答题时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "题目序号")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer itemOrder;


}
