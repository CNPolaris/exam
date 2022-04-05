package com.polaris.exam.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 学科表
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("subject")
@ApiModel(value="Subject对象", description="学科表")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学科名称")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String name;

    @ApiModelProperty(value = "年级")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer level;

    @ApiModelProperty(value = "年级名称")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String levelName;

    @ApiModelProperty(value = "排序")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer itemOrder;

    @ApiModelProperty(value = "学科状态(0.有效 1.无效)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer status;


}
