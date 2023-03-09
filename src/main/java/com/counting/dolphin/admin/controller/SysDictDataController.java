
package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.counting.dolphin.admin.domain.SysDictData;
import com.counting.dolphin.admin.service.SysDictDataService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/dict/data")
public class SysDictDataController {

    @Resource
    private SysDictDataService sysDictDataService;

    @ApiLog(title = "字典标签", businessType = BusinessType.QUERY, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/query/page")
    public R<IPage<SysDictData>> querySysDictDataPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "dictType", required = false) String dictType) {
        return R.data(sysDictDataService.querySysDictDataPage(current, size, dictType));
    }

    @ApiLog(title = "字典标签", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/createOrUpdate")
    @ResponseBody
    public R createSysDictData(@RequestBody SysDictData sysDictData) {
        return R.status(sysDictDataService.createSysDictData(sysDictData));
    }

    @ApiLog(title = "字典标签", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/remove")
    public R removeSysDictType(@RequestParam(value = "dictCode") String dictCode) {
        return R.status(sysDictDataService.removeSysDictData(dictCode));
    }

    @ApiLog(title = "字典标签状态", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/modify/status")
    public R modifyStatus(@RequestParam(value = "dictCode") String dictCode, @RequestParam(value = "status") String status) {
        return R.data(sysDictDataService.modifyStatus(dictCode, status));
    }
}