package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.counting.dolphin.admin.domain.SysLoginLog;
import com.counting.dolphin.admin.service.SysLoginLogService;
import com.counting.dolphin.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/login/log")
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    @GetMapping(value = "/query/page")
    public R<IPage<SysLoginLog>> querySysLoginLogPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "loginName", required = false) String loginName) {
        return R.data(sysLoginLogService.querySysLoginLogPage(current, size, loginName));
    }

}