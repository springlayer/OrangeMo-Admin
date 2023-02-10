package com.orange.mo.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.mo.admin.domain.SysUser;
import com.orange.mo.admin.domain.bo.SysUserDeptBo;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> iPageSelect(@Param("page") Page<SysUser> page, @Param("sysUserDeptBo") SysUserDeptBo sysUserDeptBo);
}
