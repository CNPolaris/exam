package com.polaris.exam.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class UserInfoResponse {
    private Integer id;
    private String userUuid;
    private String userName;
    private String realName;
    private Integer age;
    private Integer sex;
    private String sexStr;
    private Date birthDay;
    private Integer userLevel;
    private String userLevelStr;
    private Integer phone;
    private Integer roleId;
    private String roles;
    private Integer status;
    private String avatar;
    private Integer classId;
    private String className;
    private Date createTime;
    private Date modifyTime;
    private Date lastActiveTime;
    private Integer messageCount;
}
