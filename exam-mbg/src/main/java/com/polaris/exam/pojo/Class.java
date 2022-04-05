package com.polaris.exam.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Repository
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("class")
@ApiModel(value="Class对象", description="班级表")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "班级编码")
    private String classCode;

    @ApiModelProperty(value = "班级人数")
    private Integer classCount;

    @ApiModelProperty(value = "班级名称")
    private String className;

    private Date createTime;

    private Integer status;


}
