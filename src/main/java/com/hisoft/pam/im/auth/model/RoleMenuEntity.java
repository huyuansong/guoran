package com.hisoft.pam.im.auth.model;

import com.hisoft.pam.im.common.model.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色菜单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 自增Id
     */
    private Long id;

    /**
     * 角色Id
     */
    private  long roleId;
    /**
     * 角色名称
     */
    private  String roleName;

    /**
     * 菜单Id
     */
    private long menuId;
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 是否删除
     */
    private boolean isDelete;
}
