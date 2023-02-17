package com.orange.mo.admin.controller;

import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.domain.bo.ReSetPwd;
import com.orange.mo.admin.service.SysUserService;
import com.orange.mo.common.api.R;
import com.orange.mo.common.jwt.CurrentUserHelder;
import com.orange.mo.exception.BusinessException;
import com.orange.mo.utils.JwtTokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class LoginController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getLoginName())) {
            throw new BusinessException("用户名不能为空");
        }
        if (StringUtils.isEmpty(sysUser.getPassword())) {
            throw new BusinessException("密码不能为空");
        }
        SysUser result = sysUserService.login(sysUser);
        if (result != null) {
            return R.data(JwtTokenUtil.createToken(result));
        } else {
            throw new BusinessException("账号或密码错误");
        }
    }

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping("/user/reSetPwd")
    public R reSetPwd(@RequestBody ReSetPwd reSetPwd) {
        if (StringUtils.isEmpty(reSetPwd.getOldPassword())) {
            throw new BusinessException("旧密码不能为空");
        }
        if (StringUtils.isEmpty(reSetPwd.getNewPassword())) {
            throw new BusinessException("新密码不能为空");
        }
        if (StringUtils.isEmpty(reSetPwd.getConfirmPassword())) {
            throw new BusinessException("二次确认密码不能为空");
        }
        if (!reSetPwd.getConfirmPassword().equals(reSetPwd.getNewPassword())) {
            throw new BusinessException("两次密码不正确");
        }
        return R.status(sysUserService.reSetPwd(reSetPwd, CurrentUserHelder.currentUser().getUserId()));
    }
}
