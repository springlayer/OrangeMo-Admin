package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysDictData;
import com.counting.dolphin.admin.enums.StatusEnum;
import com.counting.dolphin.admin.mapper.SysDictDataMapper;
import com.counting.dolphin.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class SysDictDataService extends ServiceImpl<SysDictDataMapper, SysDictData> {
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    public IPage<SysDictData> querySysDictDataPage(Integer current, Integer size, String dictType) {
        Page page = new Page(current, size);
        return sysDictDataMapper.selectPage(page, Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType).orderByAsc(SysDictData::getDictSort));
    }

    public Boolean createSysDictData(SysDictData sysDictData) {
        if (ObjectUtils.isEmpty(sysDictData.getDictCode())) {
            SysDictData data = this.getOne(Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictLabel, sysDictData.getDictLabel()));
            if (null != data) {
                throw new BusinessException("字典标签重复");
            }
            sysDictData.setStatus(StatusEnum.YES.value());
            return this.save(sysDictData);
        } else {
            SysDictData data = this.getOne(Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictValue, sysDictData.getDictValue()));
            if (null != data && !data.getDictCode().equals(sysDictData.getDictCode())) {
                throw new BusinessException("字典标签重复");
            }
        }
        return this.update(sysDictData, Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictCode, sysDictData.getDictCode()));
    }

    public Boolean removeSysDictData(String dictCode) {
        return this.remove(Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictCode, dictCode));
    }

    public Boolean modifyStatus(String dictCode, String status) {
        SysDictData one = this.getOne(Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictCode, Long.valueOf(dictCode)));
        one.setStatus(Integer.valueOf(status));
        return this.update(one, Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictCode, one.getDictCode()));
    }
}