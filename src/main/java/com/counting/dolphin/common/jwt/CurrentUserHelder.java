package com.counting.dolphin.common.jwt;

import com.alibaba.fastjson.JSONObject;

public class CurrentUserHelder {

    //本地线程缓存token
    private static ThreadLocal<String> local = new ThreadLocal<>();

    /**
     * 设置token信息
     */
    public static void setUserToken(String token) {
        removeUserToken();
        local.set(token);
    }

    /**
     * 获取token信息
     *
     * @return
     */
    public static CurrentUser currentUser() {
        if (local.get() != null) {
            CurrentUser userToken = JSONObject.parseObject(local.get(), CurrentUser.class);
            return userToken;
        }
        return null;
    }

    /**
     * 移除token信息
     *
     * @return
     */
    public static void removeUserToken() {
        if (local.get() != null) {
            local.remove();
        }
    }
}