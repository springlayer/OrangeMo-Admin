package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.constant.ComConstant;
import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.mapper.SysUserMapper;
import org.springframework.stereotype.Service;


@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    public SysUser login(SysUser user) {
        return this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getLoginName, user.getLoginName()).eq(SysUser::getPassword, user.getPassword()).eq(SysUser::getStatus, ComConstant.Enable));
    }
}
