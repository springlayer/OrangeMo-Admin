package com.counting.dolphin.admin.controller;

import com.counting.dolphin.common.api.R;
import com.counting.dolphin.common.jwt.CurrentUserHelder;
import com.counting.dolphin.admin.domain.SysMenu;
import com.counting.dolphin.admin.service.SysMenuService;
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
