package com.polaris.exam.dto.paper;

import lombok.Data;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class ExamPaperTitleItemObject {
    private String name;

    private List<ExamPaperQuestionItemObject> questionItems;

}
