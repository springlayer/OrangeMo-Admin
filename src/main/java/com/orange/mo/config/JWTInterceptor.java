package com.orange.mo.config;

import com.orange.mo.common.jwt.CurrentUserHelder;
import com.orange.mo.common.jwt.JwtIgnore;
import com.orange.mo.exception.AuthException;
import com.orange.mo.exception.BusinessException;
import com.orange.mo.utils.JwtTokenUtil;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从http请求头中取出token
        final String token = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        //如果不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //如果是方法探测，直接通过
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        //如果方法有JwtIgnore注解，直接通过
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(JwtIgnore.class)) {
            JwtIgnore jwtIgnore = method.getAnnotation(JwtIgnore.class);
            if (jwtIgnore.value()) {
                return true;
            }
        }
        if (StringUtils.isEmpty(token)) {
            throw new AuthException("凭证不能为空!");
        }
        //验证，并获取token内部信息
        String userToken = JwtTokenUtil.verifyToken(token);
        //将token放入本地缓存
        CurrentUserHelder.setUserToken(userToken);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //方法结束后，移除缓存的token
        CurrentUserHelder.removeUserToken();
    }
}