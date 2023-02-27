package com.guoran.server.auth.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends BaseOfConcurrencySafeEntity {
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
