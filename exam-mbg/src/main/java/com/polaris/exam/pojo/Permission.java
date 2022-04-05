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
 * 权限表
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("permission")
@ApiModel(value="Permission对象", description="权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "权限名")
    private String name;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "资源定位符")
    private String url;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "描述")
    private String description;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "状态(0.有效 1.无效)")
    private Integer status;
    private Integer categoryId;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
