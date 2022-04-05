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
 * 题目表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("question")
@ApiModel(value="Question对象", description="题目表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问题类型(1.单选题 2.多选题 3.判断题 4.填空题 5.简答题)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer questionType;

    @ApiModelProperty(value = "学科id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer subjectId;

    @ApiModelProperty(value = "题目总分")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer score;

    @ApiModelProperty(value = "年级")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer gradeLevel;

    @ApiModelProperty(value = "难度系数")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer difficult;

    @ApiModelProperty(value = "正确答案")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String correct;

    @ApiModelProperty(value = "题目 填空、题干、解析、答案等信息")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer infoTextContentId;

    @ApiModelProperty(value = "创建人")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer createUser;

    @ApiModelProperty(value = "状态(0.有效 1.无效)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;


}
