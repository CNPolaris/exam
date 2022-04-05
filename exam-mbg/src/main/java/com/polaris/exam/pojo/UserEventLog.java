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
 * 用户日志表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_event_log")
@ApiModel(value="UserEventLog对象", description="用户日志表")
public class UserEventLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String realName;

    @ApiModelProperty(value = "内容")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String content;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;


}
