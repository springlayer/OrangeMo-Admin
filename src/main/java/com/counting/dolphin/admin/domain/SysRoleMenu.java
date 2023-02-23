package com.counting.dolphin.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName(value = "sys_role_menu")
@AllArgsConstructor
public class SysRoleMenu {
    @TableField("role_id")
    private Long roleId;
    @TableField("menu_id")
    private Long menuId;
}
