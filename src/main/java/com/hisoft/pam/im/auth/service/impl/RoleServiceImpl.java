package com.hisoft.pam.im.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hisoft.pam.im.auth.model.RoleEntity;
import com.hisoft.pam.im.auth.model.RoleMenuEntity;
import com.hisoft.pam.im.auth.model.UserRoleEntity;
import com.hisoft.pam.im.auth.repository.RoleMenuRepository;
import com.hisoft.pam.im.auth.repository.RoleRepository;
import com.hisoft.pam.im.auth.repository.UserRoleRepository;
import com.hisoft.pam.im.auth.service.RoleService;
import com.hisoft.pam.im.auth.service.UserRoleService;
import com.hisoft.pam.im.auth.service.UserService;
import com.hisoft.pam.im.auth.vmodel.RoleVM;
import com.hisoft.pam.im.auth.vmodel.UserRoleVM;
import com.hisoft.pam.im.auth.vmodel.UserVM;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.search.DynamicSearch;
import com.hisoft.pam.im.common.search.FilterGroup;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    /**
     * 根据id获取
     *
     * @param id
     */
    @Override
    public RoleVM getEntryBy(long id) {
        RoleEntity roleEntity = roleRepository.findById(id);
        if(roleEntity==null){
            return null;
        }
        RoleVM roleVM = new RoleVM();
        BeanUtils.copyProperties(roleEntity,roleVM);
        return roleVM;
    }

    /**
     * 创建
     *
     * @param roleVM
     */
    @Override
    public String createEntry(RoleVM roleVM) {
        RoleEntity roleEntity=new RoleEntity();
        List<RoleVM> roles = roleRepository.findRoleByName(roleVM.getRoleName());
        if(roles!=null && roles.size()>0){
            throw  new ServiceException("角色名不能重复.");
        }
        BeanUtils.copyProperties(roleVM,roleEntity);
        roleEntity.setCreateBy(jwtUserUtil.getUserName());
        roleEntity.setCreateTime(new Date());
        roleRepository.insert(roleEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param roleVM
     */
    @Override
    public String updateEntry(RoleVM roleVM) throws IllegalStateException{
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByRoleId(roleVM.getId());
        if(userRoleEntityList.size()>0){
            throw new ServiceException("角色已被引用，不能修改。");
        }
        RoleEntity entity=roleRepository.findById(roleVM.getId());
            if(entity==null){
                throw  new ServiceException("角色不存在.");
            }else {
                List<RoleVM> roles = roleRepository.findRoleByName(roleVM.getRoleName());
                if(roles!=null && roles.size()>0){
                    if(!roles.get(0).getId().equals(roleVM.getId())){
                        throw  new ServiceException("角色名不能重复.");
                    }
                }
            }
        //角色名不能重复
        BeanUtils.copyProperties(roleVM,entity);
        entity.setUpdateBy(jwtUserUtil.getUserName());
        entity.setUpdateTime(new Date());
        roleRepository.update(entity);
        return null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    @Transactional
    public String deleteById(long id) {
//        List<UserVM> userVMS = userService.getEntrysByRoleId(id);
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByRoleId(id);
        if(userRoleEntityList.size()>0){
            throw new ServiceException("角色已被引用，不能删除。");
        }
        roleRepository.deleteById(id);

        /**
         * 同步删除角色菜色表信息
         */
        List<RoleMenuEntity> roleMenuEntityList =roleMenuRepository.findById(id);
        if(roleMenuEntityList.size()>0){
            roleMenuRepository.deleteByRoleId(id);
        }
        return null;
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<RoleVM> findRolesByPage(PageQuery pageQuery) {


        FilterGroup filterGroup=pageQuery.getWhere();
        //自动转字符串
        String where= DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        return roleRepository.findRolesByPage(where);
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @Override
    public List<RoleVM> findAllRoles() {
        return roleRepository.findAllRoles();
    }

    /**
     * 根据角色名查找角色
     *
     * @param roleName
     * @return
     */
    @Override
    public RoleVM getEntryByName(String roleName) {
        List<RoleVM> roles = roleRepository.findRoleByName(roleName);
        if(roles!=null && roles.size()>0){
            return roles.get(0);
        }
        return null;
    }






}
