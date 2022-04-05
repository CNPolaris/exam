package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 */
public enum SexTypeEnum {
    Male(1,"男"),
    Female(0,"女");
    int code;
    String name;

    SexTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    private static final Map<Integer, SexTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (SexTypeEnum item : SexTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }
    public static SexTypeEnum fromCode(Integer code) {
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
