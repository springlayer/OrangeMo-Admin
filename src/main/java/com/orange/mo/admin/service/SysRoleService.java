package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.domain.SysRole;
import com.orange.mo.admin.domain.SysRoleMenu;
import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.domain.bo.RoleUserBo;
import com.orange.mo.admin.enums.StatusEnum;
import com.orange.mo.admin.mapper.SysRoleMapper;
import com.orange.mo.exception.BusinessException;
import com.orange.mo.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRoleDeptService sysRoleDeptService;
    @Resource
    private SysUserService sysUserService;


    public IPage<SysRole> querySysUserPage(Integer current, Integer size, String roleName) {
        Page page = new Page(current, size);
        Page<SysRole> data = sysRoleMapper.selectPage(page, Wrappers.<SysRole>lambdaQuery()
                .like(!ObjectUtils.isEmpty(roleName), SysRole::getRoleName, roleName).eq(SysRole::getDelFlag, false)
                .orderByAsc(SysRole::getRoleSort));
        if (ObjectUtils.isEmpty(data.getRecords())) {
            return data;
        }
        List<Long> roleIds = data.getRecords().stream().map(sysRole -> sysRole.getRoleId()).collect(Collectors.toList());
        Map<Long, List<SysRoleMenu>> longListMap = sysRoleMenuService.selectSysRoleByRoleIds(roleIds);
        List<SysRole> records = data.getRecords();
        for (SysRole sysRole : records) {
            List<SysRoleMenu> sysRoleMenus = longListMap.get(sysRole.getRoleId());
            if (!ObjectUtils.isEmpty(sysRoleMenus)) {
                List<Long> collect = sysRoleMenus.stream().map(sysRoleMenu -> sysRoleMenu.getMenuId()).collect(Collectors.toList());
                sysRole.setMenuIds(collect.stream().toArray(Long[]::new));
            }
        }
        data.setRecords(records);
        return data;
    }

    public Boolean removeSysRole(String roleId) {
        // 校验该角色是否存在用户
        sysUserRoleService.checkDeltRole(roleId);
        // 移除角色菜单关系
        sysRoleMenuService.removeByRoleId(roleId);
        // 移除角色部门关系
        sysRoleDeptService.removeByRoleId(roleId);
        SysRole sysRole = this.getOne(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleId, Long.valueOf(roleId)));
        sysRole.buildDelRole(sysRole);
        return this.update(sysRole, Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleId, sysRole.getRoleId()));
    }

    public Boolean createSysRole(SysRole sysRole) {
        SysRole one = this.getOne(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleKey, sysRole.getRoleKey()).eq(SysRole::getDelFlag, false));
        if (ObjectUtils.isEmpty(sysRole.getRoleId())) {
            if (null != one) {
                throw new BusinessException("角色标识重复");
            }
            SysRole role = this.getOne(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleName, sysRole.getRoleName()).eq(SysRole::getDelFlag, false));
            if (null != role) {
                throw new BusinessException("角色名称重复");
            }
            sysRole.setCreateTime(DateUtils.localDateTimeToStr(LocalDateTime.now()));
            sysRole.setDelFlag(false);
            sysRole.setStatus(StatusEnum.YES.value().toString());
            return this.save(sysRole);
        } else {
            if (null != one && !one.getRoleKey().equals(sysRole.getRoleKey())) {
                throw new BusinessException("角色标识重复");
            }
            SysRole role = this.getOne(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleName, sysRole.getRoleName()).eq(SysRole::getDelFlag, false));
            if (null != role && !one.getRoleName().equals(sysRole.getRoleName())) {
                throw new BusinessException("角色名称重复");
            }
        }
        return this.update(sysRole, Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleId, sysRole.getRoleId()));
    }

    public SysRole sysSysRoleDetail(String roleId) {
        return this.getOne(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleId, Long.valueOf(roleId)));
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean sysSysRoleAcl(SysRole sysRole) {
        sysRoleMenuService.removeByRoleId(sysRole.getRoleId().toString());
        return sysRoleMenuService.addSysRoleAcl(sysRole);
    }

    public IPage<SysUser> querySysUserInRoleIdPage(Integer current, Integer size, String roleId) {
        Page page = new Page(current, size);
        List<Long> userIds = sysUserRoleService.selectSysUserIdsByRoleId(roleId);
        if (ObjectUtils.isEmpty(userIds)) {
            return page;
        }
        return sysUserService.querySysUserPageByUserIds(page, userIds);
    }

    public Boolean removeSysUserRoleAcl(String userId, String roleId) {
        return sysUserRoleService.removeByUserIdAndRoleId(userId, roleId);
    }

    public IPage<SysUser> querySysUserUnRoleIdPage(Integer current, Integer size, String roleId, String username) {
        Page page = new Page(current, size);
        List<Long> userIds = sysUserRoleService.selectSysUserIdsByRoleId(roleId);
        return sysUserService.querySysUserPageByUnUserIds(page, userIds, username);
    }

    public Boolean createAclUserRole(RoleUserBo roleUserBo) {
        if (ObjectUtils.isEmpty(roleUserBo.getUserId())) {
            throw new BusinessException("用户不能为空");
        }
        return sysUserRoleService.createAclUserRole(roleUserBo.getUserId(), roleUserBo.getRoleId());
    }
}