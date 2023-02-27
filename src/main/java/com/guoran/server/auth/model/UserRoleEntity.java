package com.guoran.server.auth.model;


import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity extends BaseOfConcurrencySafeEntity {
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
     * 角色ID
     */
    private long roleId;
    /**
     * 角色名称
     */
    private String roleName;

}
