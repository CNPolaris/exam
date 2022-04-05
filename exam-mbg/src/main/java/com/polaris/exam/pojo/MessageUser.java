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
 * 用户消息表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("message_user")
@ApiModel(value="MessageUser对象", description="用户消息表")
public class MessageUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "消息内容id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer messageId;

    @ApiModelProperty(value = "接收人id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer receiveUserId;

    @ApiModelProperty(value = "接收人用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String receiveUserName;

    @ApiModelProperty(value = "接收人真实姓名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String receiveUserRealName;

    @ApiModelProperty(value = "是否已读(0.未读 1.已读)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Boolean readed;

    @ApiModelProperty(value = "发送时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "阅读时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date readTime;


}
