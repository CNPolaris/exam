package com.polaris.exam.dto.question;

import com.polaris.exam.dto.paper.ExamPaperSubmitItem;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class QuestionAnswer {
    private QuestionEditRequest question;
    private ExamPaperSubmitItem questionAnswer;

    public QuestionEditRequest getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEditRequest question) {
        this.question = question;
    }

    public ExamPaperSubmitItem getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(ExamPaperSubmitItem questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
