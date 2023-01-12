package com.orange.mo.admin.controller;

import com.orange.mo.common.api.R;
import com.orange.mo.common.jwt.CurrentUser;
import com.orange.mo.common.jwt.CurrentUserHelder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/sys/user")
public class SysUserController {

    @GetMapping("/info")
    @ResponseBody
    public R<CurrentUser> info() {
        return R.data(CurrentUserHelder.currentUser());
    }
}