package com.polaris.exam.dto.paper;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ExamPaperAnswerPageResponse {
    private Integer id;

    private String createTime;

    private String userScore;

    private String subjectName;

    private Integer subjectId;

    private Integer questionCount;

    private Integer questionCorrect;

    private String paperScore;

    private String doTime;

    private Integer paperType;

    private String paperTypeStr;

    private String systemScore;

    private Integer status;

    private String paperName;

    private String userName;

    private String userAccount;

    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getQuestionCorrect() {
        return questionCorrect;
    }

    public void setQuestionCorrect(Integer questionCorrect) {
        this.questionCorrect = questionCorrect;
    }

    public String getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public String getPaperTypeStr() {
        return paperTypeStr;
    }

    public void setPaperTypeStr(String paperTypeStr) {
        this.paperTypeStr = paperTypeStr;
    }

    public String getSystemScore() {
        return systemScore;
    }

    public void setSystemScore(String systemScore) {
        this.systemScore = systemScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
