package com.guoran.server.auth.service.impl;

import com.guoran.server.auth.b.repository.UserRoleRepository;
import com.guoran.server.auth.model.UserRoleEntity;
import com.guoran.server.auth.service.UserRoleService;
import com.guoran.server.auth.vmodel.RoleVM;
import com.guoran.server.auth.vmodel.UserRoleVM;
import com.guoran.server.auth.vmodel.UserRoleVMTwo;
import com.guoran.server.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    UserRoleRepository userRoleRepository;


    /**
     * 根据用户ID获取角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserRoleVM> findEntityByUserId(long userId) {
        List<UserRoleVM> userRoleVMList = new ArrayList<>();
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userId);
        for (UserRoleEntity userRoleEntity : userRoleEntityList) {
            UserRoleVM userRoleVM = new UserRoleVM();
            BeanUtils.copyProperties(userRoleEntity, userRoleVM);
            userRoleVMList.add(userRoleVM);
        }
        return userRoleVMList;
    }

    @Override
    public List<UserRoleVMTwo> findEntityByUserIdTwo(long userId) {
        List<UserRoleVMTwo> userRoleVMTwoList = new ArrayList<>();
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userId);
        for (int i = 0; i < userRoleEntityList.size(); i++) {
            UserRoleVMTwo userRoleVMTwo = new UserRoleVMTwo();
            BeanUtils.copyProperties(userRoleEntityList.get(i), userRoleVMTwo);
            userRoleVMTwoList.add(userRoleVMTwo);
        }
        return userRoleVMTwoList;
    }

    /**
     * 创建用户对应的角色
     *
     * @param userRoleVM
     */
    @Override
    @Transactional
    public String createEntry(UserRoleVM userRoleVM) {
        //角色信息
        List<RoleVM> roleVMList = userRoleVM.getRoleVMList();
        if (roleVMList.size() > 0) {
            List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
            for (RoleVM roleVM : roleVMList) {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                //用户信息
                userRoleEntity.setUserId(userRoleVM.getUserId());
                userRoleEntity.setUserName(userRoleVM.getUserName());
                //角色信息
                userRoleEntity.setRoleId(roleVM.getId());
                userRoleEntity.setRoleName(roleVM.getRoleName());
                userRoleEntity.setCreateBy(jwtUserUtil.getUserName());
                userRoleEntity.setCreateTime(new Date());
                userRoleEntityList.add(userRoleEntity);
            }
            userRoleRepository.insert(userRoleEntityList);
        }
        return null;

    }

    /**
     * 修改用户对应角色
     *
     * @param userRoleVM
     */
    @Override
    @Transactional
    public String updateEntry(UserRoleVM userRoleVM) {
        long userId = userRoleVM.getUserId();
        List<UserRoleEntity> userRoleEntity = userRoleRepository.findByUserId(userId);
        if (userRoleEntity != null && userRoleEntity.size() > 0) {
            //如果用户有之前分配的角色，先删除后增加
            userRoleRepository.deleteById(userId);
        }
        //直接调用创建接口
        this.createEntry(userRoleVM);
        return null;
    }


    /**
     * 根据用户ID删除角色
     *
     * @param userId
     */
    @Override
    @Transactional
    public void deleteEntity(long userId) {
        userRoleRepository.deleteById(userId);
    }


    /**
     * 根据用户ID修改角色
     *
     * @param entity
     */
    @Override
    @Transactional
    public void updateUserRole(UserRoleEntity entity) {
        entity.setUpdateBy(jwtUserUtil.getUserName());
        entity.setUpdateTime(new Date());
        userRoleRepository.updateUserRole(entity);
    }


    /**
     * 根据用户ID获取角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserRoleEntity findByUserIdNew(long userId) {
        return userRoleRepository.findByUserIdNew(userId);
    }

}
