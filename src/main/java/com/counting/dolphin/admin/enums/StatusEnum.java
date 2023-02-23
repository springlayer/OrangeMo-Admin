package com.counting.dolphin.admin.enums;

/**
 * @author zhihou
 * @date 2020/12/16 17:32
 * @description 公共状态
 * 0是 1否
 * 0启用 1禁用
 * 0展示 1隐藏
 */
public enum StatusEnum {

    YES("是", 1) {
        @Override
        public boolean isYes() {
            return true;
        }
    },
    NOT("否", 0) {
        @Override
        public boolean isNo() {
            return true;
        }
    };

    public Integer value() {
        return value;
    }

    public static StatusEnum value(Integer value) {
        switch (value) {
            case 1:
                return YES;
            case 0:
                return NOT;
        }
        return null;
    }

    private String name;

    private Integer value;

    StatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public boolean isYes() {
        return false;
    }

    public boolean isNo() {
        return false;
    }

}
