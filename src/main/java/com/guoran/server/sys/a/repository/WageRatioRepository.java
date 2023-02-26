package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.WageRatioEntity;
import com.guoran.server.sys.vmodel.WageRatioVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 计件工资表子表岗位工资比例数据访问层
 * @author: zhangjx
 * @create: 2020-08-21
 * @Modify By
 */
@Mapper
public interface WageRatioRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_wage_ratio where id =#{id}")
    WageRatioEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_wage_ratio (" +
            " `depart_id`, `depart_name`, `product_code`,`position_id`, `position_name`,`piece_rates`, `wage_ratio`,`create_time`, `create_by`,`position_detail`) values(" +
            "#{entity.departId}, #{entity.departName},#{entity.productCode}, #{entity.positionId}, #{entity.positionName},#{entity.pieceRates}, #{entity.wageRatio}, #{entity.createTime},#{entity.createBy},#{entity.positionDetail})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") WageRatioEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_wage_ratio set " +
            "depart_id=#{entity.departId}, depart_name=#{entity.departName}, position_id=#{entity.positionId}, position_name=#{entity.positionName},piece_rates= #{entity.wageRatio},position_detail=#{entity.positionDetail}, wage_ratio=#{entity.wageRatio}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") WageRatioEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_wage_ratio where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_wage_ratio where 1=1 ${where}")
    Page<WageRatioVM> findEntrysByPage(@Param("where") String where);

    /**
     *
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_wage_ratio" +
            " WHERE `id` in " +
            "<foreach collection='wageRatioVMS' item='wageRatioVM' open='(' close=')' separator=','>" +
            "#{wageRatioVM.id}" +
            "</foreach>" +
            "</script>"})
    void deleteBanch(@Param("wageRatioVMS") List<WageRatioVM> wageRatioVMS);

    /**
     * 批量修改
     *
     * @param wageRatioVMS
     */
    @Update({"<script>" +
            "<foreach collection='wageRatioVMS' item='entity' open='' close='' separator=';'>" +
            " update sys_wage_ratio set" +
            " depart_id=#{entity.departId}, depart_name=#{entity.departName}, position_id=#{entity.positionId}, position_name=#{entity.positionName},piece_rates= #{entity.wageRatio}, wage_ratio=#{entity.wageRatio},position_detail=#{entity.positionDetail}, update_time=#{entity.updateTime},update_by=#{entity.updateBy}" +
            " WHERE `id` = " +
            "#{entity.id}" +
            "</foreach>" +
            "</script>"})
    void updateBanch(@Param(value = "wageRatioVMS") List<WageRatioVM> wageRatioVMS);

    /**
     * 批量新增
     *
     * @param wageRatioEntities
     */
    @Insert({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " insert into " +
            " sys_wage_ratio (" +
            " `depart_id`, `depart_name`, `product_code`,`position_id`, `position_name`,`piece_rates`, `wage_ratio`,`create_time`, `create_by`,`position_detail`) values(" +
            "#{entity.departId}, #{entity.departName},#{entity.productCode}, #{entity.positionId}, #{entity.positionName},#{entity.pieceRates}, #{entity.wageRatio}, #{entity.createTime},#{entity.createBy},#{entity.positionDetail})" +
            "</foreach>" +
            "</script>"})
    void insertBanch(@Param(value = "entitys") List<WageRatioEntity> wageRatioEntities);
}
