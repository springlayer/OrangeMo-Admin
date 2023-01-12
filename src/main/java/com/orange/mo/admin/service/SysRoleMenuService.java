package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.domain.SysRoleMenu;
import com.orange.mo.admin.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuService extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> {
    public List<Long> getMenuIdsByRoles(List<Long> roles) {
        List<SysRoleMenu> list = this.list(Wrappers.<SysRoleMenu>lambdaQuery()
                .in(SysRoleMenu::getRoleId, roles));
        if (ObjectUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }
}
