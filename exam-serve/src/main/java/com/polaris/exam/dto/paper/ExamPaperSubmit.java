package com.polaris.exam.dto.paper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ExamPaperSubmit {
    @NotNull
    private Integer id;

    @NotNull
    private Integer doTime;

    private String score;

    private String createUser;
    private String doTimeStr;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDoTimeStr() {
        return doTimeStr;
    }

    public void setDoTimeStr(String doTimeStr) {
        this.doTimeStr = doTimeStr;
    }

    @NotNull
    @Valid
    private List<ExamPaperSubmitItem> answerItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoTime() {
        return doTime;
    }

    public void setDoTime(Integer doTime) {
        this.doTime = doTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<ExamPaperSubmitItem> getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List<ExamPaperSubmitItem> answerItems) {
        this.answerItems = answerItems;
    }
}
