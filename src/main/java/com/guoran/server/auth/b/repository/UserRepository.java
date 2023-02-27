package com.guoran.server.auth.b.repository;

import com.github.pagehelper.Page;
import com.guoran.server.auth.model.UserEntity;
import com.guoran.server.auth.vmodel.UserInfoVM;
import com.guoran.server.auth.vmodel.UserVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    /**
     * 返回全部用户
     *
     * @return
     */
    @Select("select * from auth_user")
    List<UserEntity> findUsers();

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    @Select("select * from auth_user where id = #{id}")
    UserEntity findById(@Param("id") long id);

    /**
     * 根据姓名查找用户
     *
     * @param real_name
     * @return
     */
    @Select("select * from auth_user where real_name = #{real_name}")
    UserEntity findByName(@Param("real_name") String real_name);

    /**
     * 根据工号查找用户
     *
     * @param userCode
     * @return
     */
    @Select("select * from auth_user where user_code = #{userCode}")
    UserEntity findByUserCode(@Param("userCode") String userCode);

    @Select("select * from auth_user where open_id = #{openId}")
    UserEntity findByOpenId(@Param("openId") String openId);

    /**
     * 新增
     *
     * @param entity
     */
    @Insert("insert into auth_user (`name`,`real_name`,`password`, `email`,`account_type`,`role_id`,`role_name`,`user_code`,`user_name`,`mobile`,`create_time`,`create_by`,`update_time`, `update_by`) values" +
            "(#{entity.name},#{entity.realName},#{entity.password},#{entity.email},#{entity.accountType},#{entity.roleId},#{entity.roleName},#{entity.userCode},#{entity.userName},#{entity.mobile},#{entity.createTime},#{entity.createBy},#{entity.updateTime},#{entity.updateBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    void insert(@Param("entity") UserEntity entity);

    /**
     * 更新
     *
     * @param entity
     */
    @Update("update auth_user set name=#{entity.name},real_name=#{entity.realName},email=#{entity.email},account_type=#{entity.accountType},role_id=#{entity.roleId},role_name=#{entity.roleName}," +
            "user_code=#{entity.userCode},user_name=#{entity.userName},mobile=#{entity.mobile},concurrency_version=concurrency_version+1," +
            " update_time=#{entity.updateTime},update_by=#{entity.updateBy}  WHERE id=#{entity.id} and concurrency_version=#{entity.concurrencyVersion}")
    void update(@Param("entity") UserEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from auth_user where id=#{id}")
    void deleteById(@Param("id") long id);

    @Select("select * from auth_user where 1=1   ${where}")
    Page<UserVM> findUsersByPage(@Param("where") String where);

    /**
     * 根据name查找用户
     *
     * @param name
     * @return
     */
    @Select("select * from auth_user where name = #{name}")
    UserVM findUserByName(@Param("name") String name);

    /**
     * 根据jobNo查找用户
     *
     * @param jobNo
     * @return
     */
    @Select("select * from auth_user where user_code = #{jobNo}")
    UserVM findUserByJobNo(@Param("jobNo") String jobNo);

    /**
     * 根据email查找用户
     *
     * @param email
     * @return
     */
    @Select("select * from auth_user where email = #{email}")
    UserEntity findUserByEmail(@Param("email") String email);

    /**
     * 根据mobile查找用户
     *
     * @param mobile
     * @return
     */
    @Select("select * from auth_user where mobile = #{mobile}")
    UserEntity findUserByMobile(@Param("mobile") String mobile);


    /**
     * 根据code查找用户
     *
     * @param code
     * @return
     */
    @Select("select * from auth_user where code = #{code}")
    UserEntity findUserByCode(@Param("code") String code);

    /**
     * 初始化密码
     * 修改密码
     *
     * @param userId
     * @param password
     */
    @Update("update auth_user set password=#{password} WHERE id=#{userId} ")
    void updateInitializePassWord(@Param("userId") long userId, @Param("password") String password);

    /**
     * 修改openId
     *
     * @param openId
     * @param userId
     */
    @Update("update auth_user set open_id=#{openId} WHERE id=#{userId} ")
    void updateOpenId(@Param("openId") String openId, @Param("userId") long userId);

    /**
     * admin
     * 用户管理
     *
     * @param where
     * @return
     */
    @Select(" select  au.id, au.`name`, au.real_name, au.email, au.account_type, aur.role_name from auth_user au " +
            " LEFT JOIN auth_user_role aur ON au.id = aur.user_id " +
            " where 1=1   ${where}")
    Page<UserInfoVM> findUserInfoAndRoleByPage(@Param("where") String where);


    /**
     * 注销账号
     *
     * @param id
     */
    @Update("update auth_user set locked=0  WHERE id=#{id} ")
    void updateAccount(@Param("id") long id);

    /**
     * 注销账号
     *
     * @param
     */
    @Update("update auth_user set locked=0  WHERE user_code=#{code} ")
    void updateAccountByCode(@Param("code") String code);

    /**
     * 查询用户信息，根据账号类型
     *
     * @param accountType
     * @return
     */
    @Select("select * from auth_user where account_type = #{accountType}")
    List<UserEntity> getUserByAccountType(@Param("accountType") long accountType);


    /**
     * 查询用户信息,根据账号id
     *
     * @param roleId
     * @return
     */
    @Select("select * from auth_user where role_id = #{roleId}")
    List<UserVM> getEntrysByRoleId(@Param("roleId") long roleId);

    /**
     * 启用账号
     *
     * @param id
     */
    @Update("update auth_user set locked=1  WHERE id=#{id} ")
    void updateAccountUse(@Param("id") long id);
}
