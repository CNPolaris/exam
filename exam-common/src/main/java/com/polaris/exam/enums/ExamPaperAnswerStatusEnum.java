package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 试卷答案状态枚举
 * @author CNPolaris
 * @version 1.0
 */
public enum ExamPaperAnswerStatusEnum {
    //待批改
    WaitJudge(0, "待批改"),
    //批改完成
    Complete(1, "完成");

    int code;
    String name;

    ExamPaperAnswerStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private static final Map<Integer, ExamPaperAnswerStatusEnum> KEY_MAP = new HashMap<>();

    static {
        for (ExamPaperAnswerStatusEnum item : ExamPaperAnswerStatusEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    public static ExamPaperAnswerStatusEnum fromCode(Integer code) {
        return KEY_MAP.get(code);
    }
}
