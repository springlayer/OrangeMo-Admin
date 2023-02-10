package com.orange.mo.admin.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class Ztree {
    /**
     * 节点ID
     */
    private Long id;

    private String text;
    private Long parentId;

    private List<Ztree> children;
}
