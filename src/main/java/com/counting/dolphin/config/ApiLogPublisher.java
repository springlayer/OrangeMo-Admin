package com.counting.dolphin.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.counting.dolphin.admin.domain.SysOperateLog;
import com.counting.dolphin.common.jwt.CurrentUserHelder;
import com.counting.dolphin.constant.ApiLog;
import com.counting.dolphin.constant.ApiLogEvent;
import com.counting.dolphin.constant.EventConstant;
import com.counting.dolphin.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 * @author houzhi
 */
@Slf4j
public class ApiLogPublisher {

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    @SneakyThrows
    public static void publishEvent(ApiLog apiLog, String methodName) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        SysOperateLog logApi = new SysOperateLog();
        logApi.setTitle(apiLog.title());
        logApi.setBusinessType(apiLog.businessType().ordinal());
        logApi.setMethod(methodName);
        logApi.setOperatorType(apiLog.operatorType().ordinal());
        logApi.setOperateName(CurrentUserHelder.currentUser().getUserName());
        addRequestInfoToLog(request, logApi);
        Map<String, Object> event = new HashMap<>(16);
        event.put(EventConstant.EVENT_LOG, logApi);
        SpringUtil.publishEvent(new ApiLogEvent(event));
    }

    /**
     * 向log中添加补齐request的信息
     *
     * @param request     请求
     * @param logAbstract 日志基础类
     */
    public static void addRequestInfoToLog(HttpServletRequest request, SysOperateLog logAbstract) throws Exception {
        if (ObjectUtils.isNotEmpty(request)) {
            final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            logAbstract.setOperateIp(ip);
            logAbstract.setOperateUrl(getPath(request.getRequestURI()));
            String address = AddressUtils.getRealAddressByIP(ip);
            logAbstract.setOperateLocation(address);
            logAbstract.setRequestMethod(request.getMethod());
            Map<String, String[]> map = request.getParameterMap();
            if (ObjectUtils.isNotEmpty(map)) {
                String params = JSON.toJSONString(map, excludePropertyPreFilter());
                logAbstract.setOperateParam(StringUtil.substring(params, 0, 2000));
            }
        }
    }

    /**
     * 忽略敏感属性
     */
    public static PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter() {
        return new PropertyPreFilters().addFilter().addExcludes(EXCLUDE_PROPERTIES);
    }

    /**
     * 获取url路径
     *
     * @param uriStr 路径
     * @return url路径
     */
    public static String getPath(String uriStr) {
        URI uri;

        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }

        return uri.getPath();
    }
}
