package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 试卷类型
 * @author CNPolaris
 * @version 1.0
 */
public enum ExamPaperTypeEnum {
    //固定试卷
    Fixed(1, "固定试卷"),
    //时段试卷
    TimeLimit(2, "时段试卷"),
    //任务试卷
    Task(3, "任务试卷"),

    Classes(4, "班级试卷");

    int code;
    String name;

    ExamPaperTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    private static final Map<Integer, ExamPaperTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (ExamPaperTypeEnum item : ExamPaperTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    public static ExamPaperTypeEnum fromCode(Integer code) {
        return KEY_MAP.get(code);
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
}
