package com.polaris.exam.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 年级
 * @author CNPolaris
 * @version 1.0
 */
public enum LevelEnum {
    K1(1,"一年级"),
    K2(2,"二年级"),
    K3(3,"三年级"),
    K4(4,"四年级"),
    K5(5,"五年级"),
    K6(6,"六年级"),
    K7(7,"初一"),
    K8(8,"初二"),
    K9(9,"初三"),
    K10(10,"高一"),
    K11(11,"高二"),
    K12(12,"高三");


    int code;
    String name;

    LevelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, LevelEnum> KEY_MAP = new HashMap<>();

    static {
        for (LevelEnum item : LevelEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }
    public static LevelEnum fromCode(Integer code) {
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
