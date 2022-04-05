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
 * 消息表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("message")
@ApiModel(value="Message对象", description="消息表")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String content;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "发送者id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer sendUserId;

    @ApiModelProperty(value = "发送者用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String sendUserName;

    @ApiModelProperty(value = "发送者真实姓名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String sendUserRealName;

    @ApiModelProperty(value = "接收人数")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer receiveUserCount;

    @ApiModelProperty(value = "已读人数")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer readCount;


}
