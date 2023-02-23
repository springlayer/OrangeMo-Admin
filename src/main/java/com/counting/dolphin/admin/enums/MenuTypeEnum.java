package com.counting.dolphin.admin.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihou
 * @date 2020/12/16 17:32
 * @description 公共状态
 * 0是 1否
 * 0启用 1禁用
 * 0展示 1隐藏
 */
public enum MenuTypeEnum {

    M("目录", "M") {
    },
    C("菜单", "C") {
    },
    F("按钮", "F") {
    };

    public String value() {
        return value;
    }

    public static List<String> addMandC() {
        List<String> objects = new ArrayList<>();
        objects.add(MenuTypeEnum.M.getValue());
        objects.add(MenuTypeEnum.C.getValue());
        return objects;
    }

    public static MenuTypeEnum value(String value) {
        switch (value) {
            case "M":
                return M;
            case "C":
                return C;
            case "F":
                return F;
        }
        return null;
    }

    private String name;

    private String value;

    MenuTypeEnum(String name, String value) {
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
