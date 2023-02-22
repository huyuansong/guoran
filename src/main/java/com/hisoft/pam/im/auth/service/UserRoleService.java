package com.hisoft.pam.im.auth.service;

import com.hisoft.pam.im.auth.model.UserRoleEntity;
import com.hisoft.pam.im.auth.vmodel.UserRoleVM;
import com.hisoft.pam.im.auth.vmodel.UserRoleVMTwo;

import java.util.List;

public interface UserRoleService {

    /**
     * 根据用户ID获取角色信息
     * @param userId
     * @return
     */
    List<UserRoleVM> findEntityByUserId(long userId);

    /**
     * 创建
     * @param userRoleVM
     */
    String createEntry(UserRoleVM userRoleVM);
    /**
     * 修改
     * @param userRoleVM
     */
    String updateEntry(UserRoleVM userRoleVM);

    /**
     * 根据用户ID获取角色信息
     * @param userId
     * @return
     */
    List<UserRoleVMTwo> findEntityByUserIdTwo(long userId);

    /**
     * 根据用户ID删除角色
     * @param userId
     */
    void deleteEntity(long userId);

    /**
     * 根据用户ID修改角色
     * @param entity
     */
    void updateUserRole(UserRoleEntity entity);


    /**
     * 根据用户ID获取角色信息
     * @param userId
     * @return
     */
    UserRoleEntity  findByUserIdNew(long userId);
}
