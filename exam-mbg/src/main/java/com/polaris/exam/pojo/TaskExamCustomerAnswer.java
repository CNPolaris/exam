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
 * 用户任务表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("task_exam_customer_answer")
@ApiModel(value="TaskExamCustomerAnswer对象", description="用户任务表")
public class TaskExamCustomerAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "任务id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer taskExamId;

    @ApiModelProperty(value = "创建者id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "内容id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer textContentId;


}
