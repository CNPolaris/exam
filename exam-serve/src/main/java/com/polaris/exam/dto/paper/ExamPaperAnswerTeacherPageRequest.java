package com.polaris.exam.dto.paper;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class ExamPaperAnswerTeacherPageRequest {
    private Integer id;
    private Integer page;
    private Integer limit;
    private Integer classId;
    private Integer subjectId;
    private Integer paperId;
    private Integer status;
}
