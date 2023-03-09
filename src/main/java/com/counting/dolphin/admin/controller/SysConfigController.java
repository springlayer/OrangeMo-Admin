package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.counting.dolphin.admin.domain.SysConfig;
import com.counting.dolphin.admin.service.SysConfigService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/config")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    @ApiLog(title = "系统常量", businessType = BusinessType.QUERY, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/query/page")
    public R<IPage<SysConfig>> querySysConfigPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "configName", required = false) String configName) {
        return R.data(sysConfigService.querySysConfigPage(current, size, configName));
    }

    @ApiLog(title = "系统常量", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/createOrUpdate")
    @ResponseBody
    public R createSysConfig(@RequestBody SysConfig sysConfig) {
        return R.status(sysConfigService.createSysConfig(sysConfig));
    }

    @ApiLog(title = "系统常量", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/remove")
    public R removeSysConfig(@RequestParam(value = "configId") String configId) {
        return R.status(sysConfigService.removeSysConfig(configId));
    }

}
