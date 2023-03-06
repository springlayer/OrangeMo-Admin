package com.counting.dolphin.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_dict_data")
public class SysDictData {
    private Long dictCode;
    private String dictSort;
    private String dictLabel;
    private String dictValue;
    private String dictType;
    private String isDefault;
    private Integer status;
    private String remark;
}