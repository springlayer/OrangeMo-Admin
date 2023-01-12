package com.orange.mo.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_menu")
public class SysRoleMenu {

    private Long roleId;
    private Long menuId;
}
