package com.polaris.exam.dto.analysis;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class StatisticsRequest {
    private Integer page;
    private Integer limit;
    private Integer classId;
    private Integer paperId;
}
