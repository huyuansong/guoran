package com.guoran.server.auth.vmodel;

import lombok.Data;


@Data
public class UserRoleVMTwo {

    /**
     * 用户
     */
    private long userId;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 角色ID
     */
    private long roleId;
    /**
     * 角色名称
     */
    private String roleName;

}
