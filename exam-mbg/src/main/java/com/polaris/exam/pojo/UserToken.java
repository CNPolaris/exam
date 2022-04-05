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
 * 用户令牌表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_token")
@ApiModel(value="UserToken对象", description="用户令牌表")
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "token令牌")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String token;

    @ApiModelProperty(value = "用户id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userName;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "截止时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date endTime;

    @ApiModelProperty(value = "状态")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;


}
