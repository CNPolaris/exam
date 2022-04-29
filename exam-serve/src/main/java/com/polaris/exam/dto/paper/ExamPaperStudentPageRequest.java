package com.polaris.exam.dto.paper;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ExamPaperStudentPageRequest {
    private Integer page;
    private Integer limit;
    private Integer subjectId;
    private Integer paperType;

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

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }
}
