package com.guoran.server.auth.vmodel;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleVM {

    /**
     * 自增Id
     */
    private Long id;

    /**
     * 用户
     */
    private long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 角色ID
     */
    private long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色
     */
    private List<RoleVM> roleVMList;
}
