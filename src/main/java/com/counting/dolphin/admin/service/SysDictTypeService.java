package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysDictData;
import com.counting.dolphin.admin.domain.SysDictType;
import com.counting.dolphin.admin.enums.StatusEnum;
import com.counting.dolphin.admin.mapper.SysDictTypeMapper;
import com.counting.dolphin.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDictTypeService extends ServiceImpl<SysDictTypeMapper, SysDictType> {
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;
    @Resource
    private SysDictDataService sysDictDataService;

    public IPage<SysDictType> querySysDictTypePage(Integer current, Integer size, String dictName) {
        Page page = new Page(current, size);
        return sysDictTypeMapper.selectPage(page, Wrappers.<SysDictType>lambdaQuery().like(!ObjectUtils.isEmpty(dictName), SysDictType::getDictName, dictName));
    }

    public Boolean removeSysDictType(String dictId) {
        SysDictType sysDictType = this.getOne(Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictId, Long.valueOf(dictId)));
        if (null != sysDictType) {
            List<SysDictData> list = sysDictDataService.list(Wrappers.lambdaQuery(SysDictData.class).eq(SysDictData::getDictType, sysDictType.getDictType()));
            if (!ObjectUtils.isEmpty(list)) {
                throw new BusinessException("存在字典标签，无法删除");
            }
        }
        return this.remove(Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictId, sysDictType.getDictId()));
    }

    public Boolean createSysDictType(SysDictType sysDictType) {
        if (ObjectUtils.isEmpty(sysDictType.getDictId())) {
            SysDictType type = this.getOne(Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictType, sysDictType.getDictType()));
            if (null != type) {
                throw new BusinessException("字典类型重复");
            }
            sysDictType.setStatus(StatusEnum.YES.value());
            return this.save(sysDictType);
        } else {
            SysDictType type = this.getOne(Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictType, sysDictType.getDictType()));
            if (null != type && !type.getDictType().equals(type.getDictType())) {
                throw new BusinessException("字典类型重复");
            }
        }
        return this.update(sysDictType, Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictId, sysDictType.getDictId()));
    }

    public Boolean modifyStatus(String dictId, String status) {
        SysDictType one = this.getOne(Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictId, Long.valueOf(dictId)));
        one.setStatus(Integer.valueOf(status));
        return this.update(one, Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictId, one.getDictId()));
    }
}
