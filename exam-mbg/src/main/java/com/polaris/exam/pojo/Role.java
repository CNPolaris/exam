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
 * 角色表
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("role")
@ApiModel(value="Role对象", description="角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色编码(1.学生 2.教师 3.管理员 4.系统管理员)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer code;

    @ApiModelProperty(value = "描述")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String description;

    @ApiModelProperty(value = "角色名称")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String name;

    @ApiModelProperty(value = "角色状态(0.有效 1.无效)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date updateTime;

    @ApiModelProperty(value = "后台用户数量")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer userCount;


}
