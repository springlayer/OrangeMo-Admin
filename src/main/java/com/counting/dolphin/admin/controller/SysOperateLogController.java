package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.counting.dolphin.admin.domain.SysOperateLog;
import com.counting.dolphin.admin.service.SysOperateLogService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/operate/log")
public class SysOperateLogController {

    @Resource
    private SysOperateLogService sysOperateLogService;

    @ApiLog(title = "操作日志", businessType = BusinessType.QUERY, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/query/page")
    public R<IPage<SysOperateLog>> querySysOperateLogPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "title", required = false) String title) {
        return R.data(sysOperateLogService.querySysOperateLogPage(current, size, title));
    }
}