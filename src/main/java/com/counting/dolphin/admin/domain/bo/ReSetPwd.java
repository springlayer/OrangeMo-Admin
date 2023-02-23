package com.counting.dolphin.admin.domain.bo;

import lombok.Data;

@Data
public class ReSetPwd {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
