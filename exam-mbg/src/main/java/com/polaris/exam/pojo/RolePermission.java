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
 * 角色权限关联表
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("role_permission")
@ApiModel(value="RolePermission对象", description="角色权限关联表")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer permissionId;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    @ApiModelProperty(value = "状态")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;

}
