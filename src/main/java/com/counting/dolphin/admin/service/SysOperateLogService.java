package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysOperateLog;
import com.counting.dolphin.admin.mapper.SysOperateLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class SysOperateLogService extends ServiceImpl<SysOperateLogMapper, SysOperateLog> {

    @Resource
    private SysOperateLogMapper sysOperateLogMapper;

    public IPage<SysOperateLog> querySysOperateLogPage(Integer current, Integer size, String title) {
        Page page = new Page(current, size);
        return sysOperateLogMapper.selectPage(page, Wrappers.<SysOperateLog>lambdaQuery()
                .like(!ObjectUtils.isEmpty(title), SysOperateLog::getTitle, title).orderByDesc(SysOperateLog::getOperateTime));
    }
}