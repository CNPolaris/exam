package com.polaris.exam.dto.paper;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ExamPaperRead {
    private ExamPaperEditRequest paper;
    private ExamPaperSubmit answer;

    public ExamPaperEditRequest getPaper() {
        return paper;
    }

    public void setPaper(ExamPaperEditRequest paper) {
        this.paper = paper;
    }

    public ExamPaperSubmit getAnswer() {
        return answer;
    }

    public void setAnswer(ExamPaperSubmit answer) {
        this.answer = answer;
    }
}
