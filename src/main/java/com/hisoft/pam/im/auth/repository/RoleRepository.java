package com.hisoft.pam.im.auth.repository;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.RoleEntity;
import com.hisoft.pam.im.auth.vmodel.RoleVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleRepository {
    /**
     * 根据id查找角色
     * @param id
     * @return
     */
    @Select("select * from auth_role where is_delete=0 and  id = #{id}")
    RoleEntity findById(@Param("id") long id);

    /**
     * 新增
     * @param entity
     */
    @Insert("insert into auth_role (`role_name`,`description`, `create_time`, `create_by`) values" +
            "(#{entity.roleName},#{entity.description},#{entity.createTime},#{entity.createBy})")
    void insert(@Param("entity") RoleEntity entity);

    /**
     * 更新
     * @param entity
     */
    @Update("update auth_role set role_name=#{entity.roleName}, description=#{entity.description},update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    void update(@Param("entity") RoleEntity entity);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from auth_role where id=#{id}")
    void deleteById(@Param("id") long id);

    /**
     * 逻辑删除
     * 更新状态
     * @param id
     */
    @Update("update auth_role set is_delete=1 where id=#{id}")
    void updateState(@Param("id") long id);


    /**
     *  校级角色权限管理
     * 分页查询
     * @param where
     * @return
     */
    @Select("select * from auth_role where is_delete=0 ${where}")
    Page<RoleVM> findRolesByPage(@Param("where") String where);

    /**
     * 根据roleName查找用户
     * @param roleName
     * @return
     */
    @Select("select * from auth_role where is_delete=0 and  role_name = #{roleName}")
    List<RoleVM> findRoleByName(@Param("roleName") String roleName);

    /**
     * 获取所有角色
     * @return
     */
    @Select("select * from auth_role where is_delete=0")
    List<RoleVM> findAllRoles();
}
