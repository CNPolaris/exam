package com.polaris.exam.dto.paper;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class ExamPaperPageRequest {
    private Integer page;
    private Integer limit;
    private Integer level;
    private Integer subjectId;
    private Integer userId;
}
