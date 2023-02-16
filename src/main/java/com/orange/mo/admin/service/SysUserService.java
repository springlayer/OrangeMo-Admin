package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.constant.ComConstant;
import com.orange.mo.admin.domain.SysDept;
import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.domain.bo.SysUserDeptBo;
import com.orange.mo.admin.enums.StatusEnum;
import com.orange.mo.admin.enums.UserTypeEnum;
import com.orange.mo.admin.mapper.SysUserMapper;
import com.orange.mo.exception.BusinessException;
import com.orange.mo.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDeptService sysDeptService;

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

    public Boolean removeSysUser(String userId) {
        SysUser sysUser = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, Long.valueOf(userId)));
        sysUser.buildDelUser(sysUser);
        return this.update(sysUser, Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, sysUser.getUserId()));
    }

    public SysUser sysUserDetail(String userId) {
        SysUser one = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, Long.valueOf(userId)).eq(SysUser::getDelFlag, false));
        SysDept sysDept = sysDeptService.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, one.getDeptId()));
        one.setDeptName(sysDept.getDeptName());
        return one;
    }

    public boolean createSysUser(SysUser sysUser) {
        if (ObjectUtils.isEmpty(sysUser.getUserId())) {
            SysUser one = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getPhone, sysUser.getPhone()).eq(SysUser::getDelFlag, false));
            if (null != one) {
                throw new BusinessException("手机号重复");
            }
            SysUser user = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getLoginName, sysUser.getLoginName()).eq(SysUser::getDelFlag, false));
            if (null != user) {
                throw new BusinessException("存在相同的登录名");
            }
            sysUser.setCreateTime(DateUtils.localDateTimeToStr(LocalDateTime.now()));
            sysUser.setDelFlag(false);
            sysUser.setUserType(UserTypeEnum.SYS_USER.getValue().toString());
            sysUser.setStatus(StatusEnum.YES.value().toString());
            return this.save(sysUser);
        } else {
            SysUser one = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getPhone, sysUser.getPhone()).eq(SysUser::getDelFlag, false));
            if (!one.getPhone().equals(sysUser.getPhone())) {
                throw new BusinessException("手机号重复");
            }
        }
        return this.update(sysUser, Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, sysUser.getUserId()));
    }
}
