package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色类型
 * @author CNPolaris
 * @version 1.0
 */
public enum UserTypeEnum {
    Admin(1,"系统管理员"),
    Teacher(2,"教师"),
    Student(3,"学生");

    int code;
    String name;
    UserTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    private static final Map<Integer, UserTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (UserTypeEnum item : UserTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }
    public static UserTypeEnum fromCode(Integer code) {
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
