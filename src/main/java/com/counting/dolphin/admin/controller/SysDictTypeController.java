package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.counting.dolphin.admin.domain.SysDictType;
import com.counting.dolphin.admin.service.SysDictTypeService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/dict")
public class SysDictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    @ApiLog(title = "字典管理", businessType = BusinessType.QUERY, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/query/page")
    public R<IPage<SysDictType>> querySysDictTypePage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "dictName", required = false) String dictName) {
        return R.data(sysDictTypeService.querySysDictTypePage(current, size, dictName));
    }

    @ApiLog(title = "字典管理", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/createOrUpdate")
    @ResponseBody
    public R createSysDictType(@RequestBody SysDictType sysDictType) {
        return R.status(sysDictTypeService.createSysDictType(sysDictType));
    }

    @ApiLog(title = "字典管理", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/remove")
    public R removeSysDictType(@RequestParam(value = "dictId") String dictId) {
        return R.status(sysDictTypeService.removeSysDictType(dictId));
    }
}