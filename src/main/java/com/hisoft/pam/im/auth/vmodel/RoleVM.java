package com.hisoft.pam.im.auth.vmodel;

import lombok.Data;

@Data
public class RoleVM {
    /**
     * 自增Id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

     /**
     * 是否删除
     */
    private boolean isDelete;
}
