package com.polaris.exam.dto;

import lombok.Data;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class AnalyseParam {
    private Integer paperId;
    private List<Integer> studentIds;

    public AnalyseParam(Integer paperId, List<Integer> studentIds) {
        this.paperId = paperId;
        this.studentIds = studentIds;
    }
}
