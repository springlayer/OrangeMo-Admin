package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.enums.MenuTypeEnum;
import com.counting.dolphin.admin.mapper.SysMenuMapper;
import com.counting.dolphin.admin.domain.SysMenu;
import com.counting.dolphin.admin.domain.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    public List<SysMenu> userMenuTreeData(Long userId) {
        List<SysMenu> menuList = selectMenuAll(userId);
        return menuList.stream()
                .filter(m -> m.getParentId() == 0)
                .peek(m -> m.setChildren(getChildren(m, menuList)))
                .collect(Collectors.toList());
    }

    public List<SysMenu> menuTreeData() {
        List<SysMenu> menuList = this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getMenuType, MenuTypeEnum.addMandC()).orderByAsc(SysMenu::getOrderNum));
        return menuList.stream()
                .filter(m -> m.getParentId() == 0)
                .peek(m -> m.setChildren(getChildren(m, menuList)))
                .collect(Collectors.toList());
    }

    private List<SysMenu> getChildren(SysMenu sysMenu, List<SysMenu> menuList) {
        return menuList.stream()
                .filter(m -> (m.getParentId().equals(sysMenu.getMenuId())))
                .peek(m -> m.setChildren(getChildren(m, menuList)))
                .collect(Collectors.toList());
    }

    public List<SysMenu> selectMenuAll(Long userId) {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId)) {
            menuList = this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getMenuType, MenuTypeEnum.addMandC()).orderByAsc(SysMenu::getOrderNum));
        } else {
            menuList = this.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    private List<SysMenu> selectMenuAllByUserId(Long userId) {
        List<Long> roles = sysUserRoleService.getUserMenuTreeData(userId);
        List<Long> menuIds = sysRoleMenuService.getMenuIdsByRoles(roles);
        return this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getMenuId, menuIds).in(SysMenu::getMenuType, MenuTypeEnum.addMandC()));
    }

}
