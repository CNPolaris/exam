package com.polaris.exam.dto.permission;

import lombok.Data;

import java.util.Date;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class PermissionResponse {
    private Integer id;
    private String name;
    private String url;
    private String description;
    private Integer status;
    private Integer categoryId;
    private String categoryName;
    private Date createTime;
}
