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
 * 用户表
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "uuid")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userUuid;

    @ApiModelProperty(value = "用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String password;

    @ApiModelProperty(value = "真实姓名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String realName;

    @ApiModelProperty(value = "年龄")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer age;

    @ApiModelProperty(value = "性别(0.女 1.男)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date birthDay;

    @ApiModelProperty(value = "年级(1-12)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer userLevel;

    @ApiModelProperty(value = "电话")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer phone;

    @ApiModelProperty(value = "角色")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer roleId;

    @ApiModelProperty(value = "状态(0.有效 1.无效)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;

    @ApiModelProperty(value = "头像地址")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date modifyTime;

    @ApiModelProperty(value = "最后一次活跃时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date lastActiveTime;


}
