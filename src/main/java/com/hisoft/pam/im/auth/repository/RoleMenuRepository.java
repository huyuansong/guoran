package com.hisoft.pam.im.auth.repository;

import com.hisoft.pam.im.auth.model.RoleMenuEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMenuRepository {


    /**
     * 根据角色ID查询角色菜单信息
     * @param roleIdList
     * @return
     */
    @Select("<script>  select * from auth_role_menu where is_delete=0 and role_id in " +
            " <foreach collection='list' item='roleId' index='index' open='(' close=')' separator=','> #{roleId}</foreach> " +
            " </script>")
     List<RoleMenuEntity> findByRoleIds(@Param("list") List roleIdList);

    /**
     * 根据角色ID查询角色信息
     * @param roleId
     * @return
     */
    @Select("select * from auth_role_menu where is_delete=0 and role_id = #{roleId}")
    List<RoleMenuEntity> findById(@Param("roleId") long roleId);

    /**
     * 新增
     * @param entity
     */
    final String INSERTM = "<script>INSERT INTO auth_role_menu (`role_id`,`role_name`,`menu_id`, `menu_name`, `create_time`, `create_by`) VALUES "+
            "<foreach collection='list' item='item' index='index' open='(' separator = '),(' close=')' >"+
            "#{item.roleId},#{item.roleName},#{item.menuId},#{item.menuName},#{item.createTime},#{item.createBy}" +
            "</foreach></script>";
    @Insert({INSERTM})
    void insert(@Param("list") List<RoleMenuEntity> entity);


    /**
     * 删除用户对应的角色信息。
     * 用于：修改用户角色的时候，
     * 先全部删除已有角色
     * 后增加新的角色
     * @param roleId
     */
    @Delete("delete from auth_role_menu where role_id=#{roleId}")
    void deleteByRoleId(@Param("roleId") long roleId);

    /**
     * 根据Id删除
     * @param id
     */
    @Delete("delete from auth_role_menu where id=#{id}")
    void deleteById(@Param("id") long id);
    /**
     * 逻辑删除用户对应的角色信息。
     * 用于：修改用户角色的时候，
     * 先全部逻辑删除已有角色
     * 后增加新的角色
     * @param roleId
     */
    @Update("update auth_role_menu set is_delete=1 where role_id=#{roleId}")
    void updateByRoleId(@Param("roleId") long roleId);
}
