package com.counting.dolphin.config;

import com.counting.dolphin.admin.domain.SysLoginLog;
import com.counting.dolphin.admin.service.SysLoginLogService;
import com.counting.dolphin.utils.*;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 */
public class AsyncFactory {

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String message, final Integer status, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                SysLoginLog sysLoginLog = new SysLoginLog();
                sysLoginLog.setStatus(status);
                String os = userAgent.getOperatingSystem();
                sysLoginLog.setOs(os);
                String browser = userAgent.getBrowser();
                sysLoginLog.setBrowser(browser);
                sysLoginLog.setStatus(status);
                sysLoginLog.setLoginName(username);
                sysLoginLog.setIpaddr(ip);
                String address = AddressUtils.getRealAddressByIP(ip);
                sysLoginLog.setLoginLocation(address);
                sysLoginLog.setLoginTime(LocalDateTime.now());
                sysLoginLog.setMsg(message);
                // 插入数据
                SpringUtils.getBean(SysLoginLogService.class).save(sysLoginLog);
            }
        };
    }
}
