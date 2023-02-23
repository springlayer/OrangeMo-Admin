package com.counting.dolphin.admin.controller;

import com.counting.dolphin.admin.domain.bo.Ztree;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.admin.service.SysDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}