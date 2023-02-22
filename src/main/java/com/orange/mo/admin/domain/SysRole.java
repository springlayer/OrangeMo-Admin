package com.orange.mo.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "sys_role")
@Data
public class SysRole {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 角色排序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private Boolean delFlag;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    @TableField(exist = false)
    private boolean flag = false;

    private String createTime;

    private String remark;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    @TableField(exist = false)
    private Long[] deptIds;

    public void buildDelRole(SysRole sysRole) {
        sysRole.setDelFlag(true);
    }
}
