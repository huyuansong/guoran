package com.hisoft.pam.im.auth.repository;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.MenuEntity;
import com.hisoft.pam.im.auth.vmodel.MenuVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuRepository {


    /**
     * 用于：南非中心，院校管理
     * 根据角色ID查询菜单信息
     * @param roleId
     * @return
     */
    @Select("<script>  select * from auth_menu where is_delete=0  and (role_id like '%${roleId}%' or role_id=999999 ) order by sort_id asc</script>")
    List<MenuEntity> findByMenuIdNew(@Param("roleId") String roleId);

    /**
     * 根据角色ID查询菜单信息
     * @param id
     * @return
     */
    @Select("<script>  select * from auth_menu where is_delete=0 and role_id=#{roleId} and id in " +
            " <foreach collection='list' item='id' index='index' open='(' close=')' separator=','> #{id}</foreach> " +
            " order by sort_id asc</script>")
    List<MenuEntity> findByMenuIds(@Param("list") List id, @Param("roleId") long roleId);


    /**
     * 查询菜单信息
     * @return
     */
    @Select("<script>  select * from auth_menu  order by sort_id asc</script>")
    List<MenuEntity> findMenuAll();

    /**
     * 根据菜单ID查询菜单信息
     * @param id
     * @return
     */
    @Select("<script>  select * from auth_menu where is_delete=0 and id in " +
            " <foreach collection='list' item='id' index='index' open='(' close=')' separator=','> #{id}</foreach> " +
            " order by sort_id asc</script>")
    List<MenuEntity> findByUserMenuIds(@Param("list") List id);


    /**
     * 根据id查找菜单
     * @param id
     * @return
     */
    @Select("select * from auth_menu where is_delete=0 and id = #{id} order by sort_id asc")
    MenuEntity findById(@Param("id") long id);

    /**
     * 新增
     * @param entity
     */
    @Insert("insert into auth_menu (`menu_name`,`description`,`icon`, `menu_link`,`parent_id`,`sort_id`,`general_url`) values" +
            "(#{entity.menuName},#{entity.description},#{entity.icon},#{entity.menuLink},#{entity.parentId},#{entity.sortId},#{entity.generalUrl})")
    void insert(@Param("entity") MenuEntity entity);

    /**
     * 更新
     * @param entity
     */
    @Update("update auth_menu set menu_name=#{entity.menuName},description=#{entity.description},icon=#{entity.icon}," +
            "menu_link=#{entity.menuLink},parent_id=#{entity.parentId},sort_id=#{entity.sortId},general_url = #{entity.generalUrl} " +
            "where id=#{entity.id}")
    void update(@Param("entity") MenuEntity entity);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from auth_menu where id=#{id}")
    void deleteById(@Param("id") long id);


    /**
     * 根据id逻辑删除
     * @param id
     */
    @Delete("update auth_menu set is_delete=1 where id=#{id}")
    void updateState(@Param("id") long id);

    /**
     * 分页查询
     * @param where
     * @return
     */
    @Select("select * from auth_menu where is_delete=0 and  ${where} order by sort_id asc")
    Page<MenuVM> findMenusByPage(@Param("where") String where);

    /**
     * 根据menuName查找菜单
     * @param menuName
     * @return
     */
    @Select("select * from auth_menu where is_delete=0 and parent_id is NULL  and menu_name = #{menuName}  order by sort_id asc")
    MenuVM findMenuByName(@Param("menuName") String menuName);

    /**
     * 根据menuName和父级ID查找菜单
     * @param menuName
     * @return
     */
    @Select("select * from auth_menu where is_delete=0 and menu_name = #{menuName} and parent_id=#{parentId} order by sort_id asc")
    MenuVM findMenuByNameAndParentId(@Param("menuName") String menuName,@Param("parentId") long parentId);


    /**
     * 根据id查找菜单
     * @param id
     * @return
     */
    @Select("select menu_name from auth_menu where is_delete=0 and id = #{id} order by sort_id asc")
    String findMenuNumeById(@Param("id") long id);

}
