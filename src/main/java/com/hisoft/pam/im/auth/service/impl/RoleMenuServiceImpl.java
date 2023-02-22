package com.hisoft.pam.im.auth.service.impl;

import com.hisoft.pam.im.auth.model.RoleMenuEntity;
import com.hisoft.pam.im.auth.repository.MenuRepository;
import com.hisoft.pam.im.auth.repository.RoleMenuRepository;
import com.hisoft.pam.im.auth.service.RoleMenuService;
import com.hisoft.pam.im.auth.vmodel.MenuVM;
import com.hisoft.pam.im.auth.vmodel.RoleMenuVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    RoleMenuRepository roleMenuRepository;
    @Autowired
    MenuRepository menuRepository;


    /**
     * 创建角色对应的菜单
     * @param roleMenuVM
     */
    @Override
    @Transactional
    public void createEntry(RoleMenuVM roleMenuVM){

        //菜单信息
        List<MenuVM> menuVMList = roleMenuVM.getMenuVMList();
        if(menuVMList.size()>0){
            //获取角色原有的菜单
            List<RoleMenuEntity> orgRoleMenus =roleMenuRepository.findById(roleMenuVM.getRoleId());
            //要删除的菜单
            List<RoleMenuEntity> delRoleMenus = new ArrayList<>();
            delRoleMenus.addAll(orgRoleMenus);
            List<RoleMenuEntity> roleMenuEntityList=new ArrayList<>();
            for(MenuVM menuVM:menuVMList){
                Optional<RoleMenuEntity> orgRoleMenu = orgRoleMenus.stream().filter(p->p.getMenuId()==menuVM.getId()).findFirst();
                if(!orgRoleMenu.isPresent()){//如果存在新增，如果已经存在不做处理
                    RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                    //角色信息
                    roleMenuEntity.setRoleId(roleMenuVM.getRoleId());
                    roleMenuEntity.setRoleName(roleMenuVM.getRoleName());
                    //菜单信息
                    roleMenuEntity.setMenuId(menuVM.getId());
                    roleMenuEntity.setMenuName(menuVM.getMenuName());
                    roleMenuEntityList.add(roleMenuEntity);
                }else{
                    delRoleMenus.remove(orgRoleMenu.get());
                }

            }
            if(roleMenuEntityList.size()>0) {
                roleMenuRepository.insert(roleMenuEntityList);
            }
            for(RoleMenuEntity roleMenuEntity:delRoleMenus){
                roleMenuRepository.deleteById(roleMenuEntity.getId());
            }
        }else{
            //删除角色的所有菜单
            roleMenuRepository.deleteByRoleId(roleMenuVM.getRoleId());
        }
    }


    /**
     * 修改角色对应的菜单
     * @param roleMenuVM
     */
    @Override
    @Transactional
    public void updateEntry(RoleMenuVM roleMenuVM){

        long roleId = roleMenuVM.getRoleId();
        List<RoleMenuEntity> roleMenuEntityList =roleMenuRepository.findById(roleId);
        if(roleMenuEntityList!=null && roleMenuEntityList.size()>0){
            //如果用户有之前分配的角色，先删除后增加
            roleMenuRepository.deleteByRoleId(roleId);
        }
        //直接调用创建接口
        this.createEntry(roleMenuVM);
    }


    /**
     * 根据角色ID返回角色菜单
     * @param roleId
     * @return
     */
    @Override
    public List<RoleMenuVM> findByRoleId(long roleId){
        List<RoleMenuEntity> roleMenuEntityList =roleMenuRepository.findById(roleId);
        List<RoleMenuVM> roleMenuVMList=new ArrayList<>();
        for(RoleMenuEntity roleMenuEntity:roleMenuEntityList){
            RoleMenuVM roleMenuVM=new RoleMenuVM();
            BeanUtils.copyProperties(roleMenuEntity,roleMenuVM);
            roleMenuVMList.add(roleMenuVM);
        }
        return roleMenuVMList;
    }


}
