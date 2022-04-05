package com.polaris.exam.event;

import com.polaris.exam.dto.paper.ExamPaperAnswerInfo;
import org.springframework.context.ApplicationEvent;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class CalculateExamPaperAnswerCompleteEvent extends ApplicationEvent {
    private final ExamPaperAnswerInfo examPaperAnswerInfo;


    public CalculateExamPaperAnswerCompleteEvent(final ExamPaperAnswerInfo examPaperAnswerInfo) {
        super(examPaperAnswerInfo);
        this.examPaperAnswerInfo = examPaperAnswerInfo;
    }

    public ExamPaperAnswerInfo getExamPaperAnswerInfo() {
        return examPaperAnswerInfo;
    }

}
