package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysLoginLog;
import com.counting.dolphin.admin.mapper.SysLoginLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class SysLoginLogService extends ServiceImpl<SysLoginLogMapper, SysLoginLog> {

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    public IPage<SysLoginLog> querySysLoginLogPage(Integer current, Integer size, String loginName) {
        Page page = new Page(current, size);
        return sysLoginLogMapper.selectPage(page, Wrappers.<SysLoginLog>lambdaQuery()
                .like(!ObjectUtils.isEmpty(loginName), SysLoginLog::getLoginName, loginName));
    }
}
