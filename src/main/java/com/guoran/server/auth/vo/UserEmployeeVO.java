package com.guoran.server.auth.vo;

import com.guoran.server.auth.vmodel.UserRoleVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 当前登录人及职工信息DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-10-10
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmployeeVO {
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

    /**
     * 档案所在公司ID  1 果然风情 2 郑铁天润
     */
    private String archivesInCompanyId;
    /**
     * 档案所在公司名称
     */
    private String archivesInCompanyName;
    /**
     * 部门ID
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 岗位ID
     */
    private String positionId;
    /**
     * 岗位名称
     */
    private String positionName;
    private List<UserRoleVM> userRoleVMList;
}
