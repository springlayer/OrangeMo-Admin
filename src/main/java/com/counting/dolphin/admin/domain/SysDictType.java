package com.counting.dolphin.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dict_type")
public class SysDictType {
    private Long dictId;
    private String dictName;
    private String dictType;
    private Integer status;
    private String remark;
}
