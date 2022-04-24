package com.polaris.exam.dto.analysis;

import lombok.Data;

import java.util.Date;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class StatisticsResponse {
    private Integer userName;
    private String realName;
    private Date startTime;
    private Date endTime;
    private Integer doTime;
    private Integer score;
    private Integer userScore;
    private Double correctRate;
    private Integer status;
}
