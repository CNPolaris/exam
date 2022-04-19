package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色类型
 * @author CNPolaris
 * @version 1.0
 */
public enum UserTypeEnum {
    Admin(1,"admin"),
    Teacher(2,"teacher"),
    Student(3,"student");

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
