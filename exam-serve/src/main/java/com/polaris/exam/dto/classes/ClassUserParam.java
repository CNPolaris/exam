package com.polaris.exam.dto.classes;

import javax.validation.constraints.NotNull;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class ClassUserParam {
    @NotNull
    private Integer classId;
    @NotNull
    private Integer userId;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
