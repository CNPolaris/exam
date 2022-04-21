package com.polaris.exam.utils;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class RespBean {
    private long code;
    private String message;
    private Object data;

    public RespBean(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public RespBean(long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     * @param message
     * @param data
     * @return
     */
    public static RespBean success(String message, Object data){
        return new RespBean(2000, message,data);
    }

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message){
        return new RespBean(2000, message);
    }
    public static RespBean success(Object data) {
        return new RespBean(2000, "成功", data);
    }

    /**
     * 失败返回结果
     * @param message
     * @param data
     * @return
     */
    public static RespBean error(String message, Object data){
        return new RespBean(1000, message,data);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(1000, message);
    }
    public static RespBean error(Object data) {
        return new RespBean(1000,"失败",data);
    }
}
