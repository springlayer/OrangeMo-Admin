package com.orange.mo.admin.enums;

/**
 * @author zhihou
 * @date 2020/12/16 17:32
 * @description 公共状态
 * 0是 1否
 * 0启用 1禁用
 * 0展示 1隐藏
 */
public enum UserTypeEnum {

    SYS_USER("系统用户", "00") {
    },
    REGISTER_USER("注册用户", "01") {
    },
    ADMIN("超级管理员用户", "0_0") {
    },
    ;

    public String value() {
        return value;
    }


    public static UserTypeEnum value(String value) {
        switch (value) {
            case "0_0":
                return ADMIN;
            case "00":
                return SYS_USER;
            case "01":
                return REGISTER_USER;
        }
        return null;
    }

    private String name;

    private String value;

    UserTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
