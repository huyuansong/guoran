package com.hisoft.pam.im.auth.service.impl;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.MenuEntity;
import com.hisoft.pam.im.auth.model.RoleMenuEntity;
import com.hisoft.pam.im.auth.model.UserRoleEntity;
import com.hisoft.pam.im.auth.repository.MenuRepository;
import com.hisoft.pam.im.auth.repository.RoleMenuRepository;
import com.hisoft.pam.im.auth.repository.UserRepository;
import com.hisoft.pam.im.auth.repository.UserRoleRepository;
import com.hisoft.pam.im.auth.service.MenuService;
import com.hisoft.pam.im.auth.service.UserService;
import com.hisoft.pam.im.auth.vmodel.MenuVM;
import com.hisoft.pam.im.auth.vmodel.NewMenuVM;
import com.hisoft.pam.im.auth.vmodel.UserVM;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.security.JwtUserUtil;
import org.docx4j.wml.U;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单管理
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Autowired
    UserService userService;
    /**
     * 获取全量的菜单
     * @return
     */
    @Override
    public List<NewMenuVM> getMenuEntity(){
        //原始数据（实体）
        List<MenuEntity> menuVMList = menuRepository.findMenuAll();
        // 最后的结果
        List<NewMenuVM> menuList = getNewMenuList(menuVMList);
        return menuList;
    }

    /**
     * 获取用户已经分配的菜单
     * @return
     */
    @Override
    public List<MenuVM> getUserMenuEntity(){
        //获取登录用户名
        String userName = jwtUserUtil.getUserName();
        //根据登录信息获取用户ID.
        UserVM userVM = userRepository.findUserByName(userName);
        //根据用户获取角色
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userVM.getId());
        if (userRoleEntityList.size()==0){
            return null;
        }
        List<Long> roleIdList=new ArrayList<>();
        for(UserRoleEntity userRoleEntity:userRoleEntityList){
            roleIdList.add(userRoleEntity.getRoleId());
        }
        //根据角色获取菜单
        List<RoleMenuEntity> roleMenuEntityList = roleMenuRepository.findByRoleIds(roleIdList);
        if(roleMenuEntityList.size()==0){
            return null;
        }
        List<Long> menuIdList = new ArrayList<>();
        for(RoleMenuEntity roleMenuEntity:roleMenuEntityList){
            menuIdList.add(roleMenuEntity.getMenuId());
        }
        List<MenuEntity> menuVMList = menuRepository.findByUserMenuIds(menuIdList);
        // 最后的结果
        List<MenuVM> menuList = getMenuList(menuVMList);
        return menuList;
    }

    /**
     * 共同的菜单转换。
     * @param menuVMList
     * @return
     */
    private List<NewMenuVM> getNewMenuList(List<MenuEntity> menuVMList){
        //原始数据（实体转VM）
        List<NewMenuVM> menuVMs = new ArrayList<>();
        for(MenuEntity menuEntity:menuVMList){
            NewMenuVM menuVM = new NewMenuVM();
            menuVM.setId(menuEntity.getId());
            menuVM.setIcon(menuEntity.getIcon());
            menuVM.setName(menuEntity.getMenuName());
            menuVM.setParentId(menuEntity.getParentId());
            menuVM.setUrl(menuEntity.getMenuLink());
            menuVM.setSortId(menuEntity.getSortId());
            menuVM.setGeneralUrl(menuEntity.getGeneralUrl());
            menuVMs.add(menuVM);
        }
        // 最后的结果
        List<NewMenuVM> menuList = new ArrayList<NewMenuVM>();

        // 先找到所有的一级菜单
        for (int i = 0; i < menuVMs.size(); i++) {
            // 一级菜单没有parentId
            if (StringUtils.isEmpty(menuVMs.get(i).getParentId())) {
                menuList.add(menuVMs.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (NewMenuVM menu : menuList) {
            menu.setChildren(getNewChild(menu.getId(), menuVMs));
        }
        return menuList;
    }
    /**
     * 共同的菜单转换。
     * @param menuVMList
     * @return
     */
    private List<MenuVM> getMenuList(List<MenuEntity> menuVMList){
        //原始数据（实体转VM）
        List<MenuVM> menuVMs = new ArrayList<>();
        for(MenuEntity menuEntity:menuVMList){
            MenuVM menuVM = new MenuVM();
            BeanUtils.copyProperties(menuEntity,menuVM);
            menuVMs.add(menuVM);
        }
        // 最后的结果
        List<MenuVM> menuList = new ArrayList<MenuVM>();

        // 先找到所有的一级菜单
        for (int i = 0; i < menuVMs.size(); i++) {
            // 一级菜单没有parentId
            if (StringUtils.isEmpty(menuVMs.get(i).getParentId())) {
                menuList.add(menuVMs.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (MenuVM menu : menuList) {
            menu.setChildMenus(getChild(menu.getId(), menuVMs));
        }
        return menuList;
    }

    /**
     * 获取用户对应角色的菜单
     * @return
     */
    @Override
    public List<MenuVM> getUserRoleMenuEntity(){
        //根据角色获取菜单
        UserVM userVM = userService.getLoginUserInfo();
        //根据用户获取角色
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userVM.getId());
        if (userRoleEntityList.size()==0){
            return null;
        }
        List<Long> roleIdList=new ArrayList<>();
        for(UserRoleEntity userRoleEntity:userRoleEntityList){
            roleIdList.add(userRoleEntity.getRoleId());
        }
        //根据角色获取菜单
        List<RoleMenuEntity> roleMenuEntityList = roleMenuRepository.findByRoleIds(roleIdList);
        if(roleMenuEntityList.size()==0){
            return null;
        }
        List<Long> menuIdList = new ArrayList<>();
        for(RoleMenuEntity roleMenuEntity:roleMenuEntityList){
            menuIdList.add(roleMenuEntity.getMenuId());
        }
        List<MenuEntity> menuVMList = menuRepository.findByUserMenuIds(menuIdList);
        // 最后的结果
        List<MenuVM> menuList = getMenuList(menuVMList);
        return menuList;
    }

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<NewMenuVM> getMenusByRole(long roleId) {
        List<RoleMenuEntity> roleMenuEntityList =roleMenuRepository.findById(roleId);
        if(roleMenuEntityList.size()==0){
            return null;
        }
        List<Long> menuIdList = new ArrayList<>();
        for(RoleMenuEntity roleMenuEntity:roleMenuEntityList){
            menuIdList.add(roleMenuEntity.getMenuId());
        }
        List<MenuEntity> menuVMList = menuRepository.findByUserMenuIds(menuIdList);
        // 最后的结果
        List<NewMenuVM> menuList = getNewMenuList(menuVMList);
        return menuList;
    }

    /**
     * 根据id获取
     *
     * @param id
     */
    @Override
    public MenuVM getEntryBy(long id){
        MenuVM menuVM = new MenuVM();
        MenuEntity menuEntity = menuRepository.findById(id);
        BeanUtils.copyProperties(menuEntity,menuVM);
        return menuVM;
    }

    /**
     * 创建
     *
     * @param menuVM
     */
    @Override
    public String createEntry(MenuVM menuVM) {
        //根据菜单名称获取是否有重复的菜单名
        // 一级菜单的判断
        if("".equals(menuVM.getParentId()) ||  menuVM.getParentId()==null){
            MenuVM vm = menuRepository.findMenuByName(menuVM.getMenuName());
            if(vm!=null){
                return JsonResult.failed("菜单名已存在");
            }
        }else {
            MenuVM vm = menuRepository.findMenuByNameAndParentId(menuVM.getMenuName(), menuVM.getParentId());
            if (vm != null) {
                return JsonResult.failed("菜单名已存在");
            }
        }
        MenuEntity menuEntity=new MenuEntity();
        BeanUtils.copyProperties(menuVM,menuEntity);
        menuEntity.setCreateBy(jwtUserUtil.getUserName());
        menuEntity.setCreateTime(new Date());
        menuRepository.insert(menuEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param menuVM
     */
    @Override
    public String updateEntry(MenuVM menuVM) throws IllegalStateException{
        MenuEntity menuEntity=menuRepository.findById(menuVM.getId());

        //根据菜单名称获取是否有重复的菜单名
        if(!menuVM.getMenuName().equals(menuEntity.getMenuName())){

            if("".equals(menuVM.getParentId()) ||  menuVM.getParentId()==null){
                MenuVM vm = menuRepository.findMenuByName(menuVM.getMenuName());
                if(vm!=null){
                    return JsonResult.failed("菜单名已存在");
                }
            }else {
                MenuVM vm = menuRepository.findMenuByNameAndParentId(menuVM.getMenuName(), menuVM.getParentId());
                if (vm != null) {
                    return JsonResult.failed("菜单名已存在");
                }
            }
        }

        BeanUtils.copyProperties(menuVM,menuEntity);
        menuEntity.setUpdateBy(jwtUserUtil.getUserName());
        menuEntity.setUpdateTime(new Date());
        menuRepository.update(menuEntity);
        return null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteById(long id) {
        menuRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param where
     * @return
     */
    @Override
    public Page<MenuVM> findMenusByPage(String where) {
        return menuRepository.findMenusByPage(where);
    }
    /**
     * 根据角色名查找角色
     *
     * @param menuName
     * @return
     */
    @Override
    public MenuVM getEntryByName(String menuName) {
        return menuRepository.findMenuByName(menuName);
    }

    @Override
    public List<MenuEntity> getAllMenu() {
        //根据角色获取菜单
        UserVM userVM = userService.getLoginUserInfo();
        //根据用户获取角色
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userVM.getId());
        if (userRoleEntityList.size()==0){
            return null;
        }
        List<Long> roleIdList=new ArrayList<>();
        for(UserRoleEntity userRoleEntity:userRoleEntityList){
            roleIdList.add(userRoleEntity.getRoleId());
        }
        //根据角色获取菜单
        List<RoleMenuEntity> roleMenuEntityList = roleMenuRepository.findByRoleIds(roleIdList);
        if(roleMenuEntityList.size()==0){
            return null;
        }
        List<Long> menuIdList = new ArrayList<>();
        for(RoleMenuEntity roleMenuEntity:roleMenuEntityList){
            menuIdList.add(roleMenuEntity.getMenuId());
        }
        List<MenuEntity> menuVMList = menuRepository.findByUserMenuIds(menuIdList);
        return menuVMList;
    }

    public List<String> getMenuList(){
        List<MenuEntity> allMenu = this.getAllMenu();
        List<String> list = new ArrayList<>();
        for(MenuEntity menuEntity : allMenu){
            if(menuEntity.getGeneralUrl()!=null){
                list.add(menuEntity.getGeneralUrl());
            }
        }
        return list;
    }

    @Override
    public List<String> getLinkList(){
        List<MenuEntity> allMenu = this.getAllMenu();
        List<String> list = new ArrayList<>();
        for(MenuEntity menuEntity : allMenu){
            if(menuEntity.getMenuLink()!=null){
                list.add(menuEntity.getMenuLink());
            }
        }
        return list;
    }

    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<MenuVM> getChild(long id, List<MenuVM> rootMenu) {
        // 子菜单
        List<MenuVM> childList = new ArrayList<>();
        for (MenuVM menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (!StringUtils.isEmpty(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (MenuVM menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isEmpty(menu.getMenuLink())) {
                // 递归
                menu.setChildMenus(getChild(menu.getId(), rootMenu));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<NewMenuVM> getNewChild(long id, List<NewMenuVM> rootMenu) {
        // 子菜单
        List<NewMenuVM> childList = new ArrayList<>();
        for (NewMenuVM menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (!StringUtils.isEmpty(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (NewMenuVM menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isEmpty(menu.getUrl())) {
                // 递归
                menu.setChildren(getNewChild(menu.getId(), rootMenu));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
