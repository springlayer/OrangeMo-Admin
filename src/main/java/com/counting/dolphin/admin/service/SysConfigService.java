package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysConfig;
import com.counting.dolphin.admin.mapper.SysConfigMapper;
import com.counting.dolphin.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class SysConfigService extends ServiceImpl<SysConfigMapper, SysConfig> {

    @Resource
    private SysConfigMapper sysConfigMapper;

    public IPage<SysConfig> querySysConfigPage(Integer current, Integer size, String configName) {
        Page page = new Page(current, size);
        return sysConfigMapper.selectPage(page, Wrappers.<SysConfig>lambdaQuery()
                .like(!ObjectUtils.isEmpty(configName), SysConfig::getConfigName, configName).eq(SysConfig::getDelFlag, false));
    }

    public Boolean createSysConfig(SysConfig sysConfig) {
        if (ObjectUtils.isEmpty(sysConfig.getConfigId())) {
            SysConfig config = this.getOne(Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigKey, sysConfig.getConfigKey()).eq(SysConfig::getDelFlag, false));
            if (null != config) {
                throw new BusinessException("常量KEY重复");
            }
            sysConfig.setDelFlag(false);
            return this.save(sysConfig);
        } else {
            SysConfig config = this.getOne(Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigKey, sysConfig.getConfigKey()).eq(SysConfig::getDelFlag, false));
            if (null != config && !config.getConfigKey().equals(sysConfig.getConfigKey())) {
                throw new BusinessException("常量KEY重复");
            }
        }
        return this.update(sysConfig, Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigId, sysConfig.getConfigId()));
    }

    public Boolean removeSysConfig(String configId) {
        SysConfig sysConfig = this.getOne(Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigId, Long.valueOf(configId)));
        if (null == sysConfig) {
            return true;
        }
        sysConfig.setDelFlag(true);
        return this.update(sysConfig, Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigId, sysConfig.getConfigId()));
    }
}
