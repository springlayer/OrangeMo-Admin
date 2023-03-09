package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysMenu;
import com.counting.dolphin.admin.domain.SysRoleMenu;
import com.counting.dolphin.admin.domain.SysUser;
import com.counting.dolphin.admin.enums.MenuTypeEnum;
import com.counting.dolphin.admin.enums.StatusEnum;
import com.counting.dolphin.admin.mapper.SysMenuMapper;
import com.counting.dolphin.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
        List<SysMenu> menuList = this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getMenuType, MenuTypeEnum.addMandC()).orderByAsc(SysMenu::getOrderNum).eq(SysMenu::getVisible, StatusEnum.YES.value().toString()));
        return menuList.stream()
                .filter(m -> m.getParentId() == 0)
                .peek(m -> m.setChildren(getChildren(m, menuList)))
                .collect(Collectors.toList());
    }

    public List<SysMenu> menuAllTreeData() {
        List<SysMenu> menuList = this.list(Wrappers.lambdaQuery(SysMenu.class).orderByAsc(SysMenu::getOrderNum).eq(SysMenu::getVisible, StatusEnum.YES.value().toString()));
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
            menuList = this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getMenuType, MenuTypeEnum.addMandC()).orderByAsc(SysMenu::getOrderNum).eq(SysMenu::getVisible, StatusEnum.YES.value().toString()));
        } else {
            menuList = this.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    private List<SysMenu> selectMenuAllByUserId(Long userId) {
        List<Long> roles = sysUserRoleService.getUserMenuTreeData(userId);
        List<Long> menuIds = sysRoleMenuService.getMenuIdsByRoles(roles);
        return this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getMenuId, menuIds).in(SysMenu::getMenuType, MenuTypeEnum.addMandC()).eq(SysMenu::getVisible, StatusEnum.YES.value().toString()));
    }

    public List<SysMenu> menuListData() {
        List<SysMenu> list = this.list(Wrappers.lambdaQuery(SysMenu.class).orderByAsc(SysMenu::getOrderNum));
        if (StringUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Long> parentIds = list.stream().map(sysMenu -> sysMenu.getParentId()).collect(Collectors.toList());
        List<SysMenu> sysMenus = this.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getParentId, parentIds));
        if (!StringUtils.isEmpty(sysMenus)) {
            Map<Long, SysMenu> sysMenuMap = sysMenus.stream().collect(Collectors.toMap(SysMenu::getMenuId,
                    Function.identity()));
            for (SysMenu sysMenu : list) {
                SysMenu menu = sysMenuMap.get(sysMenu.getParentId());
                if (null != menu) {
                    sysMenu.setParentName(menu.getMenuName());
                }
                if (sysMenu.getParentId() == 0) {
                    sysMenu.setParentName("主目录");
                }
            }
        }
        return list;
    }

    public Boolean createOrUpdate(SysMenu sysMenu) {
        if (ObjectUtils.isEmpty(sysMenu.getMenuId())) {
            SysMenu menu = this.getOne(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuName, sysMenu.getMenuName()));
            if (null != menu) {
                throw new BusinessException("存在相同的菜单名");
            }
            sysMenu.setCreateTime(LocalDateTime.now());
            sysMenu.setVisible(StatusEnum.YES.value().toString());
            return this.save(sysMenu);
        } else {
            SysMenu one = this.getOne(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuName, sysMenu.getMenuName()));
            if (null != one && !one.getMenuName().equals(sysMenu.getMenuName())) {
                throw new BusinessException("存在相同的菜单名");
            }
        }
        return this.update(sysMenu, Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuId, sysMenu.getMenuId()));
    }

    public Boolean removeMenuByMenuId(String menuId) {
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.selectSysRoleByMenuId(Long.valueOf(menuId));
        if (!ObjectUtils.isEmpty(sysRoleMenus)) {
            throw new BusinessException("角色存在菜单，请先解除绑定");
        }
        List<SysMenu> list = this.list(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getParentId, Long.valueOf(menuId)));
        if (!ObjectUtils.isEmpty(list)) {
            throw new BusinessException("存在子菜单无法删除");
        }
        return this.remove(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuId, Long.valueOf(menuId)));
    }

    public Boolean modifyStatus(String menuId, String status) {
        SysMenu one = this.getOne(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuId, Long.valueOf(menuId)));
        one.setVisible(status);
        return this.update(one, Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuId, one.getMenuId()));
    }
}
