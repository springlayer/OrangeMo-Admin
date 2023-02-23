package com.counting.dolphin.admin.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDeptBo {
    private String username;
    private String phone;
    private Long deptId;
    private String userType;
}
