package com.guoran.server.he.production.repository;

import com.github.pagehelper.Page;
import com.guoran.server.he.production.model.ManagementElectricityEntity;
import com.guoran.server.he.production.vmodel.ManagementElectricityVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @time 2023/2/2714:38
 * @outhor zhou
 */
@Mapper
public interface ManagementElectricityRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from production_management_electricity where id =#{id}")
    ManagementElectricityEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into production_management_electricity (" +
            " `department_id`, `department_name`, `meter_number`, `detailed_location`, `meter_reading_time`, `last_reading`, `this_reading`, `increment`, `meter_reader`, `audit_status`, `audit_name`,`audit_time`, `override`, `electricity_consumption`,`create_time`, `create_by`) values(" +
            "#{entity.departmentId}, #{entity.departmentName}, #{entity.meterNumber}, #{entity.detailedLocation}, #{entity.meterReadingTime}, #{entity.lastReading}, #{entity.thisReading}, #{entity.increment}, #{entity.meterReader}, #{entity.auditStatus}, #{entity.auditName},audit_time=#{entity.auditTime},#{entity.override}, #{entity.electricityConsumption}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") ManagementElectricityEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update production_management_electricity set " +
            "department_id=#{entity.departmentId}, department_name=#{entity.departmentName}, meter_number=#{entity.meterNumber}, detailed_location=#{entity.detailedLocation}, meter_reading_time=#{entity.meterReadingTime}, last_reading=#{entity.lastReading}, this_reading=#{entity.thisReading}, increment=#{entity.increment}, meter_reader=#{entity.meterReader}, audit_status=#{entity.auditStatus}, audit_name=#{entity.auditName},audit_time=#{entity.auditTime}, override=#{entity.override}, electricity_consumption=#{entity.electricityConsumption}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") ManagementElectricityEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from production_management_electricity where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from production_management_electricity where 1=1 ${where}")
    Page<ManagementElectricityVM> findEntrysByPage(@Param("where") String where);

    /*
     *审核
     */
    @Update("update production_management_electricity set " +
            " audit_status=#{entity.auditStatus},audit_name=#{entity.auditName}, audit_time=#{entity.auditTime},update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_reject_reason=#{entity.auditRejectReason} where id=#{entity.id}")
    boolean auditBy(@Param("entity") ManagementElectricityEntity entity);

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @Select("select * from production_management_electricity order by create_time desc")
    List<ManagementElectricityEntity> findAllEmpolyee();

    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from production_management_electricity where audit_status=2")
    int getBacklog();

    /**
     * 根据id删除
     *
     * @param idString
     */
    @Delete({"<script>" +
            " delete from" +
            " production_management_electricity" +
            " WHERE `id` in " +
            "<foreach collection='idString' item='id' open='(' close=')' separator=','>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    boolean deleteBanch(@Param("idString") String[] idString);
}
