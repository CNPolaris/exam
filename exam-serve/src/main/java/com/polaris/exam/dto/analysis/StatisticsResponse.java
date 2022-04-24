package com.polaris.exam.dto.analysis;

import com.polaris.exam.dto.paper.ExamPaperAnswerPageResponse;
import lombok.Data;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class StatisticsResponse {
    private Integer allStudentCount;
    private List<ExamPaperAnswerPageResponse> list;
    private Integer shouldAttend;
    private Integer attended;
    private Integer passCount;
    private Integer maxScore;
    private Integer minScore;
    private Integer avgScore;
}
