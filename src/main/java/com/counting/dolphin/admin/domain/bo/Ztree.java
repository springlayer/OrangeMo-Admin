package com.counting.dolphin.admin.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class Ztree {
    private Integer id;
    private String text;
    private Integer parentId;
    private List<Ztree> children;
}