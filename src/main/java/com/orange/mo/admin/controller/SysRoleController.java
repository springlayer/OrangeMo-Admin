package com.orange.mo.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.orange.mo.admin.domain.SysRole;
import com.orange.mo.admin.service.SysRoleService;
import com.orange.mo.common.api.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;


    @GetMapping(value = "/query/page")
    public R<IPage<SysRole>> querySysRolePage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "roleName", required = false) String roleName) {
        return R.data(sysRoleService.querySysUserPage(current, size, roleName));
    }

    @GetMapping(value = "/remove")
    public R removeSysRole(@RequestParam(value = "roleId") String roleId) {
        return R.status(sysRoleService.removeSysRole(roleId));
    }

    @PostMapping(value = "/createOrUpdate")
    @ResponseBody
    public R createSysRole(@RequestBody SysRole sysRole) {
        return R.status(sysRoleService.createSysRole(sysRole));
    }

    @GetMapping(value = "/detail")
    public R<SysRole> sysSysRoleDetail(@RequestParam(value = "roleId") String roleId) {
        return R.data(sysRoleService.sysSysRoleDetail(roleId));
    }

    @PostMapping(value = "/acl")
    public R sysSysRoleAcl(@RequestBody SysRole sysRole) {
        return R.data(sysRoleService.sysSysRoleAcl(sysRole));
    }
}