package com.polaris.exam.dto.question;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class QuestionPageStudentResponse {
    private Integer id;

    private Integer questionType;

    private String createTime;

    private String subjectName;

    private String shortTitle;

    private Integer questionScore;

    private Integer customerScore;

    private String content;

    private List<String> contentArray;

    private Integer doRight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Integer getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }

    public Integer getCustomerScore() {
        return customerScore;
    }

    public void setCustomerScore(Integer customerScore) {
        this.customerScore = customerScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getContentArray() {
        return contentArray;
    }

    public void setContentArray(List<String> contentArray) {
        this.contentArray = contentArray;
    }

    public Integer getDoRight() {
        return doRight;
    }

    public void setDoRight(Integer doRight) {
        this.doRight = doRight;
    }
}
