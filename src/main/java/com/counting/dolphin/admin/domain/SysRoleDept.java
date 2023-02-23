package com.counting.dolphin.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_role_dept")
public class SysRoleDept {
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;
}