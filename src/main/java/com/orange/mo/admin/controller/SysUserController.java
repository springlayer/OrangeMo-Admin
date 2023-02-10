package com.orange.mo.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.service.SysUserService;
import com.orange.mo.common.api.R;
import com.orange.mo.common.jwt.CurrentUser;
import com.orange.mo.common.jwt.CurrentUserHelder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/info")
    @ResponseBody
    public R<CurrentUser> info() {
        return R.data(CurrentUserHelder.currentUser());
    }

    @GetMapping(value = "/query/page")
    public R<IPage<SysUser>> querySysUserPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "username", required = false) String username,
                                              @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "deptId", required = true) Long deptId) {
        return R.data(sysUserService.querySysUserPage(current, size, username, phone, deptId));
    }
}