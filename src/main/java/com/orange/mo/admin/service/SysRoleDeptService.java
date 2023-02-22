package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.domain.SysRoleDept;
import com.orange.mo.admin.mapper.SysRoleDeptMapper;
import org.springframework.stereotype.Service;

@Service
public class SysRoleDeptService extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> {
    public void removeByRoleId(String roleId) {
        this.remove(Wrappers.lambdaQuery(SysRoleDept.class).eq(SysRoleDept::getRoleId, Long.valueOf(roleId)));
    }
}
