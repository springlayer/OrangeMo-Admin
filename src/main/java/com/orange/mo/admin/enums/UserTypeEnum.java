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

    SYS_USER("系统用户", 00) {
    },
    REGISTER_USER("注册用户", 01) {
    };

    public Integer value() {
        return value;
    }


    public static UserTypeEnum value(Integer value) {
        switch (value) {
            case 00:
                return SYS_USER;
            case 01:
                return REGISTER_USER;
        }
        return null;
    }

    private String name;

    private Integer value;

    UserTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
