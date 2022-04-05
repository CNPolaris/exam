package com.polaris.exam.dto.paper;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ExamPaperAnswerPage {
    private Integer page;
    private Integer limit;
    private Integer subjectId;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
