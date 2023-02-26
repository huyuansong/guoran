package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.WaterMeterEntity;
import com.guoran.server.sys.vmodel.WaterMeterVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 水表信息数据访问层
 * @author: zhangjx
 * @create: 2020-08-20
 * @Modify By
 */
@Mapper
public interface WaterMeterRepository {


    /**
     * 审核水表
     *
     * @param
     */
    @Update({"<script>" +
            "<foreach collection='ides' item='entity' open='' close='' separator=';'>" +
            " UPDATE" +
            " sys_water_meter" +
            " SET " +
            " `audit_status` = #{entity.auditStatus}, `dismiss_reason`=#{entity.dismissReason}," +
            " `audit_time` = #{entity.auditTime},`audit_by`=#{entity.auditBy} " +
            " WHERE `id` = " +
            " #{entity.id} " +
            "</foreach>" +
            "</script>"})
    boolean chekcProduct(@Param("ides") List<WaterMeterEntity> ides);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_water_meter where id =#{id}")
    WaterMeterEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_water_meter (" +
            " `code`, `initial_water_level`, `address`, `audit_status`,`create_time`, `create_by`) values(" +
            "#{entity.code}, #{entity.initialWaterLevel}, #{entity.address}, #{entity.auditStatus}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") WaterMeterEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_water_meter set " +
            "code=#{entity.code}, initial_water_level=#{entity.initialWaterLevel}, address=#{entity.address}, audit_status=#{entity.auditStatus}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") WaterMeterEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_water_meter where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_water_meter where 1=1 ${where}")
    Page<WaterMeterVM> findEntrysByPage(@Param("where") String where);

    /**
     * 导出
     *
     * @param
     * @return
     */
    @Select("select *from sys_water_meter")
    List<WaterMeterEntity> findAllEmpolyee();

    /**
     * 查询 code
     *
     * @param
     * @return
     */
    @Select("select * from sys_water_meter where code=#{code}")
    List<WaterMeterVM> findCodes(@Param("code") String code);

    /**
     * 根据id多项删除
     *
     * @param ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_water_meter" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    void deleteByIds(@Param("ids") String[] ids);

    /**
     * 根据id多项查询
     *
     * @param ids
     */
    @Select({"<script>" +
            " select * from" +
            " sys_water_meter" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    List<WaterMeterVM> findByIds(@Param("ids") String[] ids);

    /**
     * 审核
     *
     * @param ids
     */
    @Update({"<script>" +
            " UPDATE" +
            " sys_water_meter" +
            " SET " +
            " `audit_status` = #{state}" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    boolean checkWaterMeter(@Param("ids") String[] ids, Integer state);

    /**
     * 获取所有的信息
     *
     * @param
     */
    @Select("select *from sys_water_meter")
    void getfinds();


    @Update({"<script>" +
            " UPDATE" +
            " sys_water_meter" +
            " SET " +
            " `audit_status` = #{state},`dismiss_reason` = #{dismissReason}" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    boolean checkPieceRateWage(@Param(value = "ids") List<String> ides, @Param("state") Integer state, @Param("dismissReason") String dismissReason);

    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from sys_water_meter where audit_status=2")
    int getBacklog();
}
