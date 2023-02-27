package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.GasMeterEntity;
import com.guoran.server.sys.vmodel.GasMeterVM;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 数据访问层
 * @author: zcq
 * @create: 2020-11-27
 * @Modify By
 */
@Mapper
@Repository
public interface GasMeterRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_gas_meter where id =#{id}")
    GasMeterEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_gas_meter (" +
            " `code`, `initial_gas_level`, `address`, `audit_status`, `create_by`, `create_time`, `update_by`, `update_time`, `dismiss_reason`, `audit_by`, `audit_time`) values(" +
            "#{entity.code}, #{entity.initialGasLevel}, #{entity.address}, #{entity.auditStatus}, #{entity.createBy}, #{entity.createTime}, #{entity.updateBy}, #{entity.updateTime}, #{entity.dismissReason}, #{entity.auditBy}, #{entity.auditTime})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") GasMeterEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_gas_meter set " +
            "code=#{entity.code}, initial_gas_level=#{entity.initialGasLevel}, address=#{entity.address}, audit_status=#{entity.auditStatus}, create_by=#{entity.createBy}, create_time=#{entity.createTime}, update_by=#{entity.updateBy}, update_time=#{entity.updateTime}, dismiss_reason=#{entity.dismissReason}, audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime} where id=#{entity.id}")
    boolean update(@Param("entity") GasMeterEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_gas_meter where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_gas_meter where 1=1 ${where}")
    Page<GasMeterVM> findEntrysByPage(@Param("where") String where);

    @Update({"<script>" +
            "<foreach collection='ides' item='entity' open='' close='' separator=';'>" +
            " UPDATE" +
            " sys_gas_meter" +
            " SET " +
            " `audit_status` = #{entity.auditStatus}, `dismiss_reason`=#{entity.dismissReason}," +
            " `audit_time` = #{entity.auditTime},`audit_by`=#{entity.auditBy} " +
            " WHERE `id` = " +
            " #{entity.id} " +
            "</foreach>" +
            "</script>"})
    boolean chekcProduct(@Param("ides") List<GasMeterEntity> ides);

    /**
     * 查询 code
     *
     * @param
     * @return
     */
    @Select("select * from sys_gas_meter where code=#{code}")
    List<GasMeterVM> findCodes(@Param("code") String code);

    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from sys_gas_meter where audit_status=2")
    int getBacklog();

}
