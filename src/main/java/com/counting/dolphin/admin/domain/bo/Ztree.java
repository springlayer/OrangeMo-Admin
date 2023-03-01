package com.counting.dolphin.admin.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class Ztree {
    private Long id;
    private String text;
    private Long parentId;
    private List<Ztree> children;
}