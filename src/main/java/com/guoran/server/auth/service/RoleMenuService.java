package com.guoran.server.auth.service;

import com.guoran.server.auth.vmodel.RoleMenuVM;

import java.util.List;

public interface RoleMenuService {

    /**
     * 创建
     *
     * @param roleMenuVM
     */
    void createEntry(RoleMenuVM roleMenuVM);

    /**
     * 修改
     *
     * @param roleMenuVM
     */
    void updateEntry(RoleMenuVM roleMenuVM);

    /**
     * 根据角色ID返回菜单
     *
     * @param roleId
     * @return
     */
    List<RoleMenuVM> findByRoleId(long roleId);
}
