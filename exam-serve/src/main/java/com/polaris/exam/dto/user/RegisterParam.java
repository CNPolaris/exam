package com.polaris.exam.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterParam {
    private String userName;
    private String password;
    private Integer sex;
    private Integer userLevel;
    private Integer phone;
}
