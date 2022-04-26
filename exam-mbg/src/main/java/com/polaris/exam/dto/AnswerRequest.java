package com.polaris.exam.dto;

import com.polaris.exam.pojo.ExamPaperAnswer;
import lombok.Data;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class AnswerRequest {
    private List<ExamPaperAnswer> answerList;
}
