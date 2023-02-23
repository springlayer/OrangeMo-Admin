package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.domain.SysUserRole;
import com.orange.mo.admin.mapper.SysUserRoleMapper;
import com.orange.mo.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleMapper, SysUserRole> {

    public List<Long> getUserMenuTreeData(Long userId) {
        List<SysUserRole> list = this.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
        if (ObjectUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
    }

    public void checkDeltRole(String roleId) {
        List<SysUserRole> list = this.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, Long.valueOf(roleId)));
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        throw new BusinessException("该角色存在用户,请先取消绑定");
    }

    public void removeByRoleId(String roleId) {
        this.remove(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, Long.valueOf(roleId)));
    }

    public List<Long> selectSysUserIdsByRoleId(String roleId) {
        return this.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, roleId)).stream().map(sysUserRole -> sysUserRole.getUserId()).collect(Collectors.toList());
    }

    public Boolean removeByUserIdAndRoleId(String userId, String roleId) {
        return this.remove(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, roleId).eq(SysUserRole::getUserId, userId));
    }

    public Boolean createAclUserRole(List<Long> userId, Long roleId) {
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long l : userId) {
            sysUserRoles.add(new SysUserRole(l, roleId));
        }
        return this.saveBatch(sysUserRoles);
    }
}
