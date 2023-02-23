package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysRoleDept;
import com.counting.dolphin.admin.mapper.SysRoleDeptMapper;
import org.springframework.stereotype.Service;

@Service
public class SysRoleDeptService extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> {
    public void removeByRoleId(String roleId) {
        this.remove(Wrappers.lambdaQuery(SysRoleDept.class).eq(SysRoleDept::getRoleId, Long.valueOf(roleId)));
    }
}
