package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.ElectricityMeterEntity;
import com.guoran.server.sys.vmodel.ElectricityMeterVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 电表信息数据访问层
 * @author: zhangjx
 * @create: 2020-08-20
 * @Modify By
 */
@Mapper
public interface ElectricityMeterRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("SELECT * from sys_electricity_meter where id = #{id}")
    ElectricityMeterEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_electricity_meter (" +
            " `code`, `initial_read`, `override`, `initial_degree`, `address`, `audit_status`, `audit_by`, `audit_time`,`create_time`, `create_by`) values(" +
            "#{entity.code}, #{entity.initialRead}, #{entity.override}, #{entity.initialDegree}, #{entity.address}, #{entity.auditStatus}, #{entity.auditBy}, #{entity.auditTime}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") ElectricityMeterEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_electricity_meter set " +
            "code=#{entity.code}, initial_read=#{entity.initialRead}, override=#{entity.override}, initial_degree=#{entity.initialDegree}, address=#{entity.address}, audit_status=#{entity.auditStatus}, audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") ElectricityMeterEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_electricity_meter where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_electricity_meter where 1=1 ${where} order by id desc")
    Page<ElectricityMeterVM> findEntrysByPage(@Param("where") String where);

    /**
     * 获取所有电表信息
     *
     * @param
     * @return
     */
    @Select("select *from sys_electricity_meter")
    List<ElectricityMeterEntity> findAllEmpolyee();

    /**
     * 查询状态码
     *
     * @param
     * @return
     */
    @Select("select *from sys_electricity_meter where code = #{code}")
    List<ElectricityMeterVM> findCodes(@Param("code") String code);

    /**
     * 多项删除
     *
     * @param
     * @return
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_electricity_meter" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    void deleteByIds(@Param("ids") String[] ids);


    /**
     * 多项查询
     *
     * @param
     * @return
     */
    @Select({"<script>" +
            " select * from" +
            " sys_electricity_meter" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    List<ElectricityMeterVM> findByIds(@Param("ids") String[] ids);

    /**
     * 审核
     *
     * @param state
     * @return
     */
    @Update({"<script>" +
            " UPDATE" +
            " sys_electricity_meter" +
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
    @Select("select COUNT(id) from sys_electricity_meter where audit_status=2")
    int getBacklog();

    /**
     * 审核电表
     *
     * @param
     */
    @Update({"<script>" +
            "<foreach collection='ides' item='entity' open='' close='' separator=';'>" +
            " UPDATE" +
            " sys_electricity_meter" +
            " SET " +
            " `audit_status` = #{entity.auditStatus}, `dismiss_reason`=#{entity.dismissReason}," +
            " `audit_time` = #{entity.auditTime},`audit_by`=#{entity.auditBy} " +
            " WHERE `id` = " +
            " #{entity.id} " +
            "</foreach>" +
            "</script>"})
    boolean chekcProduct(@Param("ides") List<ElectricityMeterEntity> ides);
}
