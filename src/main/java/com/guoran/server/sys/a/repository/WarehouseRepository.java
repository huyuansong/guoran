package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.WarehouseEntity;
import com.guoran.server.sys.vmodel.WarehouseVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 仓库信息表数据访问层
 * @author: zhangjx
 * @create: 2020-08-20
 * @Modify By
 */
@Mapper
public interface WarehouseRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_warehouse where id =#{id}")
    WarehouseEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_warehouse (" +
            " `warehouse_code`, `warehouse_name`, `user_code`, `user_name`, `mobile`, `audit_status`,`pk_org`,`orgcode`,`orgname`,`enablestate`,`create_time`, `create_by`, `update_by`, `update_time`, `audit_by`, `warehouse_pk`) values(" +
            "#{entity.warehouseCode}, #{entity.warehouseName}, #{entity.userCode}, #{entity.userName}, #{entity.mobile}, #{entity.auditStatus}, #{entity.pkOrg},#{entity.orgCode},#{entity.orgName},#{entity.enableState},#{entity.createTime},#{entity.createBy}, #{entity.updateBy}, #{entity.updateTime}, #{entity.auditBy}, #{entity.warehousePk})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") WarehouseEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_warehouse set " +
            "warehouse_code=#{entity.warehouseCode}, warehouse_name=#{entity.warehouseName}, user_code=#{entity.userCode}, user_name=#{entity.userName}, mobile=#{entity.mobile}, audit_status=#{entity.auditStatus}, pk_org=#{entity.pkOrg}, orgcode=#{entity.orgCode}, orgname=#{entity.orgName}, enablestate=#{entity.enableState}, update_time=#{entity.updateTime},update_by=#{entity.updateBy}, audit_by=#{entity.auditBy} , warehouse_pk=#{entity.warehousePk} where id=#{entity.id}")
    boolean update(@Param("entity") WarehouseEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_warehouse set " +
            "warehouse_code=#{entity.warehouseCode}, warehouse_name=#{entity.warehouseName}, user_code=#{entity.userCode}, user_name=#{entity.userName}, mobile=#{entity.mobile}, audit_status=#{entity.auditStatus}, pk_org=#{entity.pkOrg}, orgcode=#{entity.orgCode}, orgname=#{entity.orgName}, enablestate=#{entity.enableState}, update_time=#{entity.updateTime},update_by=#{entity.updateBy}, audit_by=#{entity.auditBy} where warehouse_pk=#{entity.warehousePk}")
    boolean updateByPk(@Param("entity") WarehouseEntity entity);

    /**
     * 根据id删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_warehouse" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    boolean deleteById(@Param("ids") String[] Ids);

    /**
     * 根据主键删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_warehouse" +
            " WHERE `warehouse_pk` in " +
            "<foreach collection='ids' item='warehousePk' open='(' close=')' separator=','>" +
            "#{warehousePk}" +
            "</foreach>" +
            "</script>"})
    boolean deleteByPk(@Param("ids") String[] Ids);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_warehouse where 1=1 ${where} order by create_time desc")
    Page<WarehouseVM> findEntrysByPage(@Param("where") String where);

    /**
     * 获取所有仓库
     */
    @Select("select * from sys_warehouse ")
    List<WarehouseEntity> getAllWarehouseEntry();

    /**
     * 审核仓库
     *
     * @param
     */
    @Update({"<script>" +
            " UPDATE" +
            " sys_warehouse" +
            " SET " +
            " `audit_status` = #{state}, `dismiss_reason`=#{dismissReason}, `audit_by`=#{entity.auditBy}, `update_time`=#{entity.updateTime}, update_by=#{entity.updateBy} " +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    boolean chekcProduct(@Param("entity") WarehouseEntity entity, @Param("ids") List<String> ides, @Param("state") int state, @Param("dismissReason") String dismissReason);

    /**
     * 根据code查询信息
     */
    @Select("select * from sys_warehouse where warehouse_code =#{warehouseCode}")
    WarehouseEntity findByCode(@Param("warehouseCode") String warehouseCode);

    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from sys_warehouse where audit_status=2")
    int getBacklog();

    /**
     * 根据商品名字查询
     *
     * @return
     */
    @Select("select * from sys_warehouse where warehouse_name =#{warehouseName}")
    WarehouseEntity findByName(@Param("warehouseName") String warehouseName);

    /**
     * 根据code查询信息
     */
    @Select("select * from sys_warehouse where warehouse_pk =#{warehousePk}")
    WarehouseEntity findByPK(@Param("warehousePk") String warehousePk);

    @Update("update sys_warehouse set " +
            "enablestate= 2 where warehouse_pk=#{id}")
    boolean enableByPk(@Param("id") String id);

    @Update("update sys_warehouse set " +
            "enablestate= 1 where warehouse_pk=#{id}")
    boolean disableByPk(@Param("id") String id);
}

