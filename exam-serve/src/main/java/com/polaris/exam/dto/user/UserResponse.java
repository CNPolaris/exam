package com.polaris.exam.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class UserResponse {

    private Integer id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    private String sex;

    private LocalDateTime birthDay;

    private String userLevel;

    private Integer phone;

    private String role;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private LocalDateTime lastActiveTime;


}
