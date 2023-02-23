package com.guoran.server.bu.equipment.repository;


import com.github.pagehelper.Page;
import com.guoran.server.bu.equipment.model.PurifiedBottleRunInfoEntity;
import com.guoran.server.bu.equipment.vmodel.PurifiedBottleRunInfoVM;
import org.apache.ibatis.annotations.*;

/**
 * w
 * 2023/1/25  15:04
 * 01-pro
 **/
@Mapper
public interface PurifiedBottleRunInfoRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from purified_bottle_run_info where id =#{id}")
    PurifiedBottleRunInfoEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into purified_bottle_run_info (" +
            " `depart_id`, `depart_name`, `prodect_code`, `prodect_name`, `specifications`, `note_taker`, `note_taker_time`, `program_num`, `host_run_speed`, `glue_machine_temperature`, `rubber_hose_temperature`, `one_temperature`, `two_temperature`, `three_temperature`, `four_temperature`, `five_temperature`, `production_date`, `remark`,`create_time`, `create_by`) values(" +
            "#{entity.departId}, #{entity.departName}, #{entity.prodectCode}, #{entity.prodectName}, #{entity.specifications}, #{entity.noteTaker}, #{entity.noteTakerTime}, #{entity.programNum}, #{entity.hostRunSpeed}, #{entity.glueMachineTemperature}, #{entity.rubberHoseTemperature}, #{entity.oneTemperature}, #{entity.twoTemperature}, #{entity.threeTemperature}, #{entity.fourTemperature}, #{entity.fiveTemperature}, #{entity.productionDate}, #{entity.remark}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") PurifiedBottleRunInfoEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update purified_bottle_run_info set " +
            "depart_id=#{entity.departId}, depart_name=#{entity.departName}, prodect_code=#{entity.prodectCode}, prodect_name=#{entity.prodectName}, specifications=#{entity.specifications}, note_taker=#{entity.noteTaker}, note_taker_time=#{entity.noteTakerTime}, program_num=#{entity.programNum}, host_run_speed=#{entity.hostRunSpeed}, glue_machine_temperature=#{entity.glueMachineTemperature}, rubber_hose_temperature=#{entity.rubberHoseTemperature}, one_temperature=#{entity.oneTemperature}, two_temperature=#{entity.twoTemperature}, three_temperature=#{entity.threeTemperature}, four_temperature=#{entity.fourTemperature}, five_temperature=#{entity.fiveTemperature}, production_date=#{entity.productionDate}, remark=#{entity.remark}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") PurifiedBottleRunInfoEntity entity);

    /**
     * 根据id删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " purified_bottle_run_info" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    boolean deleteById(@Param("ids") String[] Ids);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from purified_bottle_run_info where 1=1 ${where} order by create_time desc")
    Page<PurifiedBottleRunInfoVM> findEntrysByPage(@Param("where") String where);

}

