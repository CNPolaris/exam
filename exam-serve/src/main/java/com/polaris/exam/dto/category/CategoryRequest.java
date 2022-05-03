package com.polaris.exam.dto.category;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class CategoryRequest {
    private Integer page;
    private Integer limit;
    private String name;
}
