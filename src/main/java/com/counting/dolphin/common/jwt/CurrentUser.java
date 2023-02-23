package com.counting.dolphin.common.jwt;

import lombok.Data;

@Data
public class CurrentUser {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

}
