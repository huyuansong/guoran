package com.hisoft.pam.im.auth.model;

import com.hisoft.pam.im.common.model.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 自增Id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账号类型
     */
    private int accountType;
    /**
     * 角色id
     */
    private int roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 员工编号：YG+6位序号
     */
    private String userCode;
    /**
     * 员工姓名
     */
    private String userName;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 账户状态
     */
    private boolean locked;
    /**
     * 微信OpenID
     */
    private String openId;
}
