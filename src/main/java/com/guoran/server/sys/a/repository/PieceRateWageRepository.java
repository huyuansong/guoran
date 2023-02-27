package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.PieceRateWageEntity;
import com.guoran.server.sys.vmodel.PieceRateWageVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 计件工资表数据访问层
 * @author: zhangjx
 * @create: 2020-08-21
 * @Modify By
 */
@Mapper
public interface PieceRateWageRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_piece_rate_wage where id =#{id}")
    PieceRateWageEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_piece_rate_wage (" +
            " `depart_id`, `depart_name`,`main_measurement`, `product_code`, `product_name`, `specifications`, `piece_rates`, `audit_status`, `audit_by`, `audit_time`,`create_time`, `create_by`) values(" +
            "#{entity.departId}, #{entity.departName},#{entity.mainMeasurement}, #{entity.productCode}, #{entity.productName}, #{entity.specifications}, #{entity.pieceRates}, #{entity.auditStatus}, #{entity.auditBy}, #{entity.auditTime}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") PieceRateWageEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_piece_rate_wage set " +
            "depart_id=#{entity.departId}, depart_name=#{entity.departName},main_measurement=#{entity.mainMeasurement}, product_code=#{entity.productCode}, product_name=#{entity.productName}, specifications=#{entity.specifications}, piece_rates=#{entity.pieceRates}, audit_status=#{entity.auditStatus}, audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime}, dismiss_reason=#{entity.dismissReason},  update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") PieceRateWageEntity entity);

    /**
     * 根据id删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_piece_rate_wage" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id}" +
            "</foreach>" +
            "</script>"})
    boolean deleteById(@Param("ids") String[] Ids);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_piece_rate_wage where 1=1 ${where}")
    Page<PieceRateWageVM> findEntrysByPage(@Param("where") String where);

    /**
     * 查询所有信息
     *
     * @return
     */
    @Select("select * from sys_piece_rate_wage")
    List<PieceRateWageEntity> findAllProduct();

    /**
     * 审核
     *
     * @param
     * @param
     * @return
     */
    @Update({"<script>" +
            "<foreach collection='pieceRateWageEntities' item='entity' open='' close='' separator=';'>" +
            " UPDATE" +
            " sys_piece_rate_wage" +
            " SET " +
            " `audit_status` = #{entity.auditStatus},`dismiss_reason` = #{entity.dismissReason}," +
            " `audit_time` = #{entity.auditTime},`audit_by` = #{entity.auditBy} " +
            " WHERE `id` = " +
            "#{entity.id}" +
            "</foreach>" +
            "</script>"})
    boolean checkPieceRateWage(@Param(value = "pieceRateWageEntities") List<PieceRateWageEntity> pieceRateWageEntities);


    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from sys_piece_rate_wage where audit_status=2")
    int getBacklog();
}
