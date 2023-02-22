package com.hisoft.pam.im.auth.vmodel;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuVM {

    /**
     * 自增Id
     */
    private Long id;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色
     */
    private List<MenuVM> menuVMList;

    /**
     * 是否删除
     */
    private boolean isDelete;
}
