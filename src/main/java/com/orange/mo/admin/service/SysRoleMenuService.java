package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.domain.SysRole;
import com.orange.mo.admin.domain.SysRoleMenu;
import com.orange.mo.admin.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public void removeByRoleId(String roleId) {
        this.remove(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, Long.valueOf(roleId)));
    }

    public Boolean addSysRoleAcl(SysRole sysRole) {
        Long[] menuIds = sysRole.getMenuIds();
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        for (Long s : menuIds) {
            sysRoleMenus.add(new SysRoleMenu(sysRole.getRoleId(), s));
        }
        return this.saveBatch(sysRoleMenus);
    }

    public Map<Long, List<SysRoleMenu>> selectSysRoleByRoleIds(List<Long> roleIds) {
        List<SysRoleMenu> list = this.list(Wrappers.<SysRoleMenu>lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleIds));
        return list.stream().collect(Collectors.groupingBy(SysRoleMenu::getRoleId));
    }
}