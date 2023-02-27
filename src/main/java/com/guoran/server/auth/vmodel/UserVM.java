package com.guoran.server.auth.vmodel;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserVM {
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
     * 密码
     */
    private String password;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 确认密码
     */
    private String confirmPassword;
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
    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 密码最后重置时间
     */
    private Date lastPasswordResetDate;

    /**
     * 角色
     */
    private List<RoleVM> roleVMList;

    /**
     * 验证码
     */
    private String captcha;


}
