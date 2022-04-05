package com.polaris.exam.dto.paper;

import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.ExamPaperQuestionCustomerAnswer;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ExamPaperAnswerInfo {
    public ExamPaper examPaper;
    public ExamPaperAnswer examPaperAnswer;
    public List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers;

    public ExamPaper getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(ExamPaper examPaper) {
        this.examPaper = examPaper;
    }

    public ExamPaperAnswer getExamPaperAnswer() {
        return examPaperAnswer;
    }

    public void setExamPaperAnswer(ExamPaperAnswer examPaperAnswer) {
        this.examPaperAnswer = examPaperAnswer;
    }

    public List<ExamPaperQuestionCustomerAnswer> getExamPaperQuestionCustomerAnswers() {
        return examPaperQuestionCustomerAnswers;
    }

    public void setExamPaperQuestionCustomerAnswers(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers) {
        this.examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswers;
    }
}
