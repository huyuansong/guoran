package com.guoran.server.auth.service;

import com.github.pagehelper.Page;
import com.guoran.server.auth.model.MenuEntity;
import com.guoran.server.auth.vmodel.MenuVM;
import com.guoran.server.auth.vmodel.NewMenuVM;

import java.util.List;

public interface MenuService {

    /**
     * 获取全量菜单
     *
     * @return
     */
    List<NewMenuVM> getMenuEntity();

    /**
     * 获取用户已经分配的菜单
     *
     * @return
     */
    List<MenuVM> getUserMenuEntity();

    /**
     * 获取用户对应角色的菜单
     *
     * @return
     */
    List<MenuVM> getUserRoleMenuEntity();

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    List<NewMenuVM> getMenusByRole(long roleId);

    /**
     * 根据id获取
     *
     * @param id
     */
    MenuVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param menuVM
     */
    String createEntry(MenuVM menuVM);

    /**
     * 修改
     *
     * @param menuVM
     */
    String updateEntry(MenuVM menuVM);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(long id);

    /**
     * 分页
     *
     * @param where
     * @return
     */
    Page<MenuVM> findMenusByPage(String where);

    /**
     * 根据菜单名查找菜单
     *
     * @param menuName
     * @return
     */
    MenuVM getEntryByName(String menuName);

    /**
     * 获取全量菜单
     *
     * @return
     */
    List<MenuEntity> getAllMenu();

    /**
     * 获取全量路由
     *
     * @return
     */
    List<String> getLinkList();
}
