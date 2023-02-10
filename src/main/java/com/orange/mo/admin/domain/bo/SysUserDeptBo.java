package com.orange.mo.admin.domain.bo;

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
}
