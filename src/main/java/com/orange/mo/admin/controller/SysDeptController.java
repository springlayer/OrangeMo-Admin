package com.orange.mo.admin.controller;

import com.orange.mo.admin.domain.bo.Ztree;
import com.orange.mo.admin.service.SysDeptService;
import com.orange.mo.common.api.R;
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