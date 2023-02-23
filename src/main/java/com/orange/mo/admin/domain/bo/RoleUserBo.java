package com.orange.mo.admin.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class RoleUserBo {
    private List<Long> userId;
    private Long roleId;
}
