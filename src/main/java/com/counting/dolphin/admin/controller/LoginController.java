package com.counting.dolphin.admin.controller;

import com.counting.dolphin.admin.domain.SysUser;
import com.counting.dolphin.admin.domain.bo.ReSetPwd;
import com.counting.dolphin.admin.enums.StatusEnum;
import com.counting.dolphin.admin.service.SysUserService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.common.jwt.CurrentUserHelder;
import com.counting.dolphin.config.AsyncFactory;
import com.counting.dolphin.config.AsyncManager;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import com.counting.dolphin.exception.BusinessException;
import com.counting.dolphin.utils.JwtTokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
            if(StatusEnum.value(Integer.valueOf(result.getStatus())).isNo()){
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(sysUser.getLoginName(), "账号被禁用", StatusEnum.NOT.value()));
                throw new BusinessException("账号被禁用");
            }
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(sysUser.getLoginName(), "登录成功", StatusEnum.YES.value()));
            return R.data(JwtTokenUtil.createToken(result));
        } else {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(sysUser.getLoginName(), "账号或密码错误", StatusEnum.NOT.value()));
            throw new BusinessException("账号或密码错误");
        }
    }

    /**
     * 重置密码
     *
     * @return
     */
    @ApiLog(title = "重置密码", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
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