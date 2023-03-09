package com.counting.dolphin.admin.controller;

import com.counting.dolphin.admin.domain.SysMenu;
import com.counting.dolphin.admin.service.SysMenuService;
import com.counting.dolphin.common.api.R;
import com.counting.dolphin.common.jwt.CurrentUserHelder;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.BusinessType;
import com.counting.dolphin.constant.OperatorType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all/ztree")
    @ResponseBody
    public R<List<SysMenu>> allZtree() {
        return R.data(sysMenuService.menuAllTreeData());
    }

    @GetMapping("/list")
    @ResponseBody
    public R<List<SysMenu>> list() {
        return R.data(sysMenuService.menuListData());
    }

    @ApiLog(title = "菜单管理", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/createOrUpdate")
    @ResponseBody
    public R createOrUpdate(@RequestBody SysMenu sysMenu) {
        return R.status(sysMenuService.createOrUpdate(sysMenu));
    }

    @ApiLog(title = "菜单管理", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/remove")
    @ResponseBody
    public R removeMenu(@RequestParam("menuId") String menuId) {
        return R.status(sysMenuService.removeMenuByMenuId(menuId));
    }

    @ApiLog(title = "菜单状态", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/modify/status")
    public R modifyStatus(@RequestParam(value = "menuId") String menuId, @RequestParam(value = "status") String status) {
        return R.data(sysMenuService.modifyStatus(menuId, status));
    }
}
