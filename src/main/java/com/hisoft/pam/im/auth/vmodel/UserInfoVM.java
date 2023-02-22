package com.hisoft.pam.im.auth.vmodel;

import lombok.Data;

@Data
public class UserInfoVM {
    /**
     * 自增Id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账号类型
     */
    private int accountType;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 账户状态
     */
    private boolean locked;
}
