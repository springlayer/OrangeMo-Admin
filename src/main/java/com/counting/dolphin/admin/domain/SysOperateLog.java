package com.counting.dolphin.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_operate_log")
public class SysOperateLog {
    private Long operateId;
    private String title;
    private Integer businessType;
    private String method;
    private String requestMethod;
    private Integer operatorType;
    private String operateName;
    private String operateUrl;
    private String operateIp;
    private String operateLocation;
    private String operateParam;
    private String jsonResult;
    private Integer status;
    private String errorMsg;
    private LocalDateTime operateTime;
}