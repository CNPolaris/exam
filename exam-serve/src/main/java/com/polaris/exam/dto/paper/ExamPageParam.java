package com.polaris.exam.dto.paper;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class ExamPageParam {
    private Integer id;
    private Integer subjectId;
    private Integer classId;
    private Integer level;
    private Integer page;
    private Integer limit;
}
