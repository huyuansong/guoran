package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.ElectricityMeterEntity;
import com.guoran.server.sys.model.MeterDepartEntity;
import com.guoran.server.sys.model.WaterMeterEntity;
import com.guoran.server.sys.vmodel.MeterDepartVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 水表，电表关联部门使用比例
 *
 * @description: 数据访问层
 * @author: zhangjx
 * @create: 2020-08-20
 * @Modify By
 */
@Mapper
public interface MeterDepartRepository {
    /**
     * 批量新增部门及对应的分滩比例
     *
     * @param entity
     */
    String INSERTM = "<script>INSERT INTO sys_meter_depart (`meter_type`, `type_code`, `depart_id`, `depart_name`, `apportionment_ratio`,`create_time`, `create_by`) VALUES " +
            "<foreach collection='entity' item='item' index='index' open='(' separator = '),(' close=')' >" +
            "#{item.meterType}, #{item.typeCode}, #{item.departId}, #{item.departName}, #{item.apportionmentRatio}, #{item.createTime},#{item.createBy} " +
            "</foreach>" +
            "</script>";

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_meter_depart where id =#{id}")
    MeterDepartEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert({"insert into sys_meter_depart (" +
            " `meter_type`,`type_code`, `depart_id`, `depart_name`, `apportionment_ratio`,`create_time`, `create_by`) values(" +
            "#{entity.meterType}, #{entity.typeCode},#{entity.departId}, #{entity.departName}, #{entity.apportionmentRatio}, #{entity.createTime},#{entity.createBy})"})
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") MeterDepartEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_meter_depart set " +
            "meter_type=#{entity.meterType}, depart_id=#{entity.departId}, depart_name=#{entity.departName}, apportionment_ratio=#{entity.apportionmentRatio}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") MeterDepartEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_meter_depart where id =#{id}")
    boolean deleteById(@Param("id") long id);

    @Delete({"<script>" +
            " delete from" +
            " sys_meter_depart" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    void deleteByIdss(@Param("id") List<Long> id);

    /**
     * 根据code删除
     *
     * @param code
     */
    @Delete("delete from sys_meter_depart where type_code=#{code}")
    boolean deleteByIds(@Param("code") String code);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_meter_depart where 1=1 ${where}")
    Page<MeterDepartVM> findEntrysByPage(@Param("where") String where);

    @Insert({INSERTM})
    void insertBatch(@Param("entity") List<MeterDepartEntity> entity);

    /**
     * 批量删除电表
     *
     * @param
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_meter_depart" +
            " WHERE `type_code` in " +
            "<foreach collection='ElectricityMeterEntitys' item='ElectricityMeterEntity' open='(' close=')' separator=','>" +
            "#{ElectricityMeterEntity.code}" +
            "</foreach>" +
            "</script>"})
    boolean deleteByTypeCode(@Param("ElectricityMeterEntitys") List<ElectricityMeterEntity> ElectricityMeterEntitys);

    /**
     * 批量删除水表
     *
     * @param
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_meter_depart" +
            " WHERE `type_code` in " +
            "<foreach collection='waterMeterEntities' item='waterMeterEntitie' open='(' close=')' separator=','>" +
            "#{waterMeterEntitie.code}" +
            "</foreach>" +
            "</script>"})
    boolean deleteByTypeCodes(@Param("waterMeterEntities") List<WaterMeterEntity> waterMeterEntities);

    /**
     * 根据Code值进行查找
     *
     * @param
     */
    @Select("select * from sys_meter_depart where type_code = #{code}")
    MeterDepartEntity findcode(@Param("code") String code);

    /**
     * 批量修改
     *
     * @param
     */
    @Update({"<script>" +
            "<foreach collection='meterDepartVMS' item='item' open='' close='' separator=';'>" +
            " update sys_meter_depart set" +
            " meter_type = #{item.meterType},type_code = #{item.typeCode}, depart_id = #{item.departId}, depart_name = #{item.departName}, apportionment_ratio = #{item.apportionmentRatio}, update_time = #{item.updateTime}, update_by = #{item.updateBy} " +
            " WHERE `id` = " +
            "#{item.id}" +
            "</foreach>" +
            "</script>"})
    void updateBanch(@Param("meterDepartVMS") List<MeterDepartVM> meterDepartVMS);
}
