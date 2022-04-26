package com.polaris.exam.dto.analysis;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class AttendResponse {
    private Integer id;
    private String userName;
    private String realName;
    private Integer attendCount;
    private Integer shouldAttendCount;
    private Integer correctCount;
    private Integer questionCount;
    private Integer maxScore;
    private Integer minScore;
    private Double avgScore;
}
