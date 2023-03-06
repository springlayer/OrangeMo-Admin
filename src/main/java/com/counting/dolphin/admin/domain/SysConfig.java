package com.counting.dolphin.admin.domain;

import lombok.Data;

@Data
public class SysConfig {
    private Long configId;
    private String configName;
    private String configKey;
    private String configValue;
    private String configType;
    private String remark;
    private Boolean delFlag;
}