package com.counting.dolphin.config;

import com.counting.dolphin.admin.domain.SysOperateLog;
import com.counting.dolphin.admin.service.SysOperateLogService;
import com.counting.dolphin.constant.ApiLogEvent;
import com.counting.dolphin.constant.EventConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.Map;


/**
 * 异步监听日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

    private final SysOperateLogService sysOperateLogService;

    @Async
    @Order
    @EventListener(ApiLogEvent.class)
    public void saveApiLog(ApiLogEvent event) {
        Map<String, Object> source = (Map<String, Object>) event.getSource();
        SysOperateLog logApi = (SysOperateLog) source.get(EventConstant.EVENT_LOG);
        logApi.setOperateTime(LocalDateTime.now());
        sysOperateLogService.save(logApi);
    }
}