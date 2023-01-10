package com.orange.mo.admin.controller;

import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.service.SysUserService;
import com.orange.mo.common.api.R;
import com.orange.mo.exception.BusinessException;
import com.orange.mo.utils.JwtTokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/login")
    public R login(@RequestParam String username, @RequestParam String password) {
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException("密码不能为空");
        }
        SysUser user = new SysUser(username, password);
        SysUser result = sysUserService.login(user);
        if (result != null) {
            return R.data(JwtTokenUtil.createToken(result));
        } else {
            throw new BusinessException("账号或密码错误！");
        }
    }

}
