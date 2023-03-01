package com.counting.dolphin.admin.controller;

import com.counting.dolphin.admin.domain.SysDept;
import com.counting.dolphin.admin.domain.bo.Ztree;
import com.counting.dolphin.admin.service.SysDeptService;
import com.counting.dolphin.common.api.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/sys/dept")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @GetMapping("/treeData")
    @ResponseBody
    public R<List<Ztree>> queryDeptZtreeData() {
        return R.data(sysDeptService.queryDeptZtreeData());
    }

    @GetMapping("/list")
    @ResponseBody
    public R<List<SysDept>> list() {
        return R.data(sysDeptService.deptListData());
    }

    @PostMapping("/createOrUpdate")
    @ResponseBody
    public R createOrUpdate(@RequestBody SysDept sysDept) {
        return R.status(sysDeptService.createOrUpdate(sysDept));
    }

    @GetMapping("/remove")
    @ResponseBody
    public R removeDept(@RequestParam("deptId") String deptId) {
        return R.status(sysDeptService.removeDeptByDeptId(deptId));
    }
}