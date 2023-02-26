package com.guoran.server.auth.b.repository;

import com.guoran.server.auth.model.UserRoleEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRoleRepository {


    /**
     * 新增
     *
     * @param entity
     */
    String INSERTM = "<script>INSERT INTO auth_user_role (`user_id`, `user_name`,`role_id`,`role_name`,`create_time`, `create_by`,`update_time`, `update_by`) VALUES " +
            "<foreach collection='list' item='item' index='index' open='(' separator = '),(' close=')' >" +
            "#{item.userId},#{item.userName},#{item.roleId},#{item.roleName},#{item.createTime},#{item.createBy},#{item.updateTime},#{item.updateBy}" +
            "</foreach></script>";

    /**
     * 根据用户ID查询用户相关的角色信息
     *
     * @param userId
     * @return
     */
    @Select("select * from auth_user_role where user_id = #{userId}")
    List<UserRoleEntity> findByUserId(@Param("userId") long userId);

    /**
     * 根据角色ID查询
     *
     * @param roleId
     * @return
     */
    @Select("select * from auth_user_role where role_id = #{roleId}")
    List<UserRoleEntity> findByRoleId(@Param("roleId") long roleId);

    /**
     * 根据用户ID查询用户相关的角色信息
     *
     * @param userId
     * @return
     */
    @Select("select * from auth_user_role where user_id = #{userId}")
    UserRoleEntity findByUserIdNew(@Param("userId") long userId);

    @Insert({INSERTM})
    void insert(@Param("list") List<UserRoleEntity> entity);


    /**
     * 删除用户对应的角色信息。
     * 用于：修改用户角色的时候，
     * 先全部删除已有角色
     * 后增加新的角色
     *
     * @param userId
     */
    @Delete("delete from auth_user_role where user_id=#{userId}")
    void deleteById(@Param("userId") long userId);

    /**
     * 根据用户ID修改角色
     *
     * @param entity
     */
    @Update("update auth_user_role set role_id=#{entity.roleId},role_name=#{entity.roleName},update_time=#{entity.updateTime},update_by=#{entity.updateBy}  where user_id=#{entity.userId}")
    void updateUserRole(@Param("entity") UserRoleEntity entity);

}
