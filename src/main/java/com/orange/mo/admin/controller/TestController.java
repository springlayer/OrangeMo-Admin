package com.orange.mo.admin.controller;

import com.orange.mo.common.api.R;
import com.orange.mo.common.jwt.CurrentUserHelder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/hello")
    public R test() {
        return R.data(CurrentUserHelder.currentUser());
    }
}