
package com.counting.dolphin.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.counting.dolphin.admin.domain.SysDictData;
import com.counting.dolphin.admin.service.SysDictDataService;
import com.counting.dolphin.common.api.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sys/dict/data")
public class SysDictDataController {

    @Resource
    private SysDictDataService sysDictDataService;

    @GetMapping(value = "/query/page")
    public R<IPage<SysDictData>> querySysDictDataPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "dictType", required = false) String dictType) {
        return R.data(sysDictDataService.querySysDictDataPage(current, size, dictType));
    }

    @PostMapping(value = "/createOrUpdate")
    @ResponseBody
    public R createSysDictData(@RequestBody SysDictData sysDictData) {
        return R.status(sysDictDataService.createSysDictData(sysDictData));
    }

    @GetMapping(value = "/remove")
    public R removeSysDictType(@RequestParam(value = "dictCode") String dictCode) {
        return R.status(sysDictDataService.removeSysDictData(dictCode));
    }
}