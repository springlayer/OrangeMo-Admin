package com.counting.dolphin.config;

import com.counting.dolphin.admin.service.SysOperateLogService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author houzhi
 * @date 2022-4-14
 * @description
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogToolAutoConfiguration {

    private final SysOperateLogService sysOperateLogService;

    @Bean
    public ApiLogListener apiLogListener() {
        return new ApiLogListener(sysOperateLogService);
    }
}