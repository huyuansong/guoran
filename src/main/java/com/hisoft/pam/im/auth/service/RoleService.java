package com.hisoft.pam.im.auth.service;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.vmodel.RoleVM;
import com.hisoft.pam.im.common.search.PageQuery;

import java.util.List;

public interface RoleService {

    /**
     *  根据id获取
     * @param id
     */
    RoleVM getEntryBy(long id);

    /**
     * 创建
     * @param roleVM
     */
    String createEntry(RoleVM roleVM);
    /**
     * 修改
     * @param roleVM
     */
    String updateEntry(RoleVM roleVM);
    /**
     * 删除
     * @param id
     */
    String deleteById(long id);

    /**
     * 分页
     * @param pageQuery
     * @return
     */
    Page<RoleVM> findRolesByPage(PageQuery pageQuery);

    /**
     * 获取所有角色
     * @return
     */
    List<RoleVM> findAllRoles();

    /**
     * 根据角色名查找角色
     * @param roleName
     * @return
     */
    RoleVM getEntryByName(String roleName);

}
