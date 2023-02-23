package com.counting.dolphin.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.counting.dolphin.admin.domain.SysUser;
import com.counting.dolphin.exception.AuthException;

import java.util.Date;

public class JwtTokenUtil {

    //定义token返回头部
    public static final String AUTH_HEADER_KEY = "Authorization";

    //token前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    //签名密钥
    public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x";

    //有效期默认为 2hour
    public static final Long EXPIRATION_TIME = 1000L * 60 * 60 * 8;


    /**
     * 创建TOKEN
     *
     * @param sysUser
     * @return
     */
    public static String createToken(SysUser sysUser) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(JSON.toJSONString(sysUser))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(KEY));
    }

    /**
     * 验证token
     *
     * @param token
     */
    public static String verifyToken(String token) throws Exception {
        try {
            return JWT.require(Algorithm.HMAC512(KEY))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
        } catch (TokenExpiredException e) {
            throw new AuthException("token已失效，请重新登录");
        } catch (JWTVerificationException e) {
            throw new AuthException("token验证失败！");
        }
    }
}