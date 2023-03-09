package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.counting.dolphin.admin.domain.SysUser;
import com.counting.dolphin.admin.service.SysUserService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.common.jwt.CurrentUser;
import com.counting.dolphin.common.jwt.CurrentUserHelder;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/info")
    @ResponseBody
    public R<SysUser> info() {
        CurrentUser currentUser = CurrentUserHelder.currentUser();
        SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, currentUser.getUserId()));
        return R.data(one);
    }

    @ApiLog(title = "用户管理", businessType = BusinessType.QUERY, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/query/page")
    public R<IPage<SysUser>> querySysUserPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "username", required = false) String username,
                                              @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "deptId", required = true) Long deptId) {
        return R.data(sysUserService.querySysUserPage(current, size, username, phone, deptId));
    }

    @ApiLog(title = "用户管理", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/remove")
    public R removeSysUser(@RequestParam(value = "userId") String userId) {
        return R.status(sysUserService.removeSysUser(userId));
    }

    @ApiLog(title = "用户管理", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/createOrUpdate")
    @ResponseBody
    public R createSysUser(@RequestBody SysUser sysUser) {
        return R.status(sysUserService.createSysUser(sysUser));
    }

    @ApiLog(title = "用户详情", businessType = BusinessType.QUERY, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/detail")
    public R<SysUser> sysUserDetail(@RequestParam(value = "userId") String userId) {
        return R.data(sysUserService.sysUserDetail(userId));
    }
}