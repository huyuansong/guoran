package com.guoran.server.auth.service;

import com.github.pagehelper.Page;
import com.guoran.server.auth.model.UserEntity;
import com.guoran.server.auth.vmodel.UserInfoVM;
import com.guoran.server.auth.vmodel.UserVM;
import com.guoran.server.auth.vo.UserEmployeeVO;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.EmployeeVM;

import java.util.List;


public interface UserService {
    /**
     * 根据id获取
     *
     * @param id
     */
    UserVM getEntryBy(long id);

    /**
     * 根据id获取
     *
     * @param id
     */
    UserEntity getEntryById(long id);

    /**
     * 创建
     *
     * @param userVM
     */
    Long createEntry(UserVM userVM);

    /**
     * 修改
     *
     * @param userVM
     */
    void updateEntry(UserVM userVM);

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
    Page<UserVM> findUsersByPage(String where);

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    UserVM getEntryByName(String username);

    /**
     * 根据工号查找用户
     *
     * @param userCode
     * @return
     */
    String getEntryByJobNo(String userCode);


    /**
     * 初始化密码
     *
     * @param userId
     */
    void updateInitializePassWord(long userId);

    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    Long getLoginUser();

    /**
     * 获取当前登录用户
     *
     * @return
     */
    UserVM getLoginUserInfo();

    /**
     * 获取当前登录用户
     *
     * @return
     */
    UserEmployeeVO getCurrentUserInfo();

    /**
     * 修改密码
     *
     * @param userVM
     */
    String updatePassWord(UserVM userVM);

    /**
     * 重置密码
     *
     * @param userVM
     * @return
     */
    String resetPassword(UserVM userVM);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<UserInfoVM> findUserInfoAndRoleByPage(PageQuery pageQuery);


    /**
     * 注销账号
     *
     * @param id
     */
    String updateAccount(long id);

    /**
     * 注销账号
     *
     * @param
     */
    String updateAccountByCode(String code);


    /**
     * 根据Code获取
     *
     * @param code
     */
    UserVM getEntryByCode(String code);


    /**
     * 根据Mobile获取
     *
     * @param mobile
     */
    UserVM getEntryByMobile(String mobile);


    /**
     * 根据email获取
     *
     * @param email
     */
    UserVM getEntryByEmail(String email);

    /**
     * 根据roleId获取用户
     *
     * @param roleId
     */
    List<UserVM> getEntrysByRoleId(long roleId);


    /**
     * 查询用户信息，根据账号类型
     *
     * @param accountType
     * @return
     */
    List<UserEntity> getUserByAccountType(long accountType);

    /**
     * 启用账户
     *
     * @param id
     * @return
     */
    String updateAccountUse(long id);

    /**
     * 修改openId
     *
     * @param userVM
     */
    String updateOpenId(UserVM userVM);

    /**
     * 根据用户id获取员工信息
     *
     * @param id
     * @return
     */
    EmployeeVM getEmpInfo(long id);
}
