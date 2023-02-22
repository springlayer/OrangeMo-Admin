package com.orange.mo.admin.controller;

import com.orange.mo.admin.domain.SysMenu;
import com.orange.mo.admin.service.SysMenuService;
import com.orange.mo.common.api.R;
import com.orange.mo.common.jwt.CurrentUserHelder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/user/ztree")
    @ResponseBody
    public R<List<SysMenu>> info() {
        return R.data(sysMenuService.userMenuTreeData(CurrentUserHelder.currentUser().getUserId()));
    }

    @GetMapping("/ztree")
    @ResponseBody
    public R<List<SysMenu>> ztree() {
        return R.data(sysMenuService.menuTreeData());
    }
}
