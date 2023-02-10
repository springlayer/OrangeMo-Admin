package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.constant.ComConstant;
import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.domain.bo.SysUserDeptBo;
import com.orange.mo.admin.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser login(SysUser user) {
        return this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getLoginName, user.getLoginName()).eq(SysUser::getPassword, user.getPassword()).eq(SysUser::getStatus, ComConstant.Enable));
    }

    public IPage<SysUser> querySysUserPage(Integer current, Integer size, String username, String phone, Long deptId) {
        Page<SysUser> page = new Page<SysUser>(current, size);
        SysUserDeptBo sysUserDeptBo = new SysUserDeptBo(username, phone, deptId);
        IPage<SysUser> sysUserIPage = sysUserMapper.iPageSelect(page, sysUserDeptBo);
        if (StringUtils.isEmpty(sysUserIPage)) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        return sysUserIPage;
    }
}
