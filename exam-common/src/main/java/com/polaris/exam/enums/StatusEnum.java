package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态
 * @author polaris
 */

public enum StatusEnum {
    NO(0, "无效"),
    OK(1,"有效"),
    Publish(2, "发布");

    int code;
    String name;

    StatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    private static final Map<Integer, StatusEnum> KEY_MAP = new HashMap<>();
    static {
        for (StatusEnum item : StatusEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }
    public static StatusEnum fromCode(Integer code) {
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
