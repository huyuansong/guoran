package com.guoran.server.bu.equipment.repository;


import com.github.pagehelper.Page;
import com.guoran.server.bu.equipment.model.PurifiedFillingInfoEntity;
import com.guoran.server.bu.equipment.vmodel.PurifiedFillingInfoVM;
import org.apache.ibatis.annotations.*;

/**
 * w
 * 2023/1/26  19:12
 * 01-pro
 **/
@Mapper
public interface PurifiedFillingInfoRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from purified_filling_info where id =#{id}")
    PurifiedFillingInfoEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into purified_filling_info (" +
            " `depart_id`, `depart_name`, `prodect_code`, `prodect_name`, `specifications`, `note_taker`, `note_taker_time`, `level`, `bottle_feeding_fan`, `host`, `feed_pump`, `bottle_delivery`, `blow_valve`, `ligai`, `top_cover_fan`, `upper_cover_conveying`, `remark`,`create_time`, `create_by`) values(" +
            "#{entity.departId}, #{entity.departName}, #{entity.prodectCode}, #{entity.prodectName}, #{entity.specifications}, #{entity.noteTaker}, #{entity.noteTakerTime}, #{entity.level}, #{entity.bottleFeedingFan}, #{entity.host}, #{entity.feedPump}, #{entity.bottleDelivery}, #{entity.blowValve}, #{entity.ligai}, #{entity.topCoverFan}, #{entity.upperCoverConveying}, #{entity.remark}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") PurifiedFillingInfoEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update purified_filling_info set " +
            "depart_id=#{entity.departId}, depart_name=#{entity.departName}, prodect_code=#{entity.prodectCode}, prodect_name=#{entity.prodectName}, specifications=#{entity.specifications}, note_taker=#{entity.noteTaker}, note_taker_time=#{entity.noteTakerTime}, level=#{entity.level}, bottle_feeding_fan=#{entity.bottleFeedingFan}, host=#{entity.host}, feed_pump=#{entity.feedPump}, bottle_delivery=#{entity.bottleDelivery}, blow_valve=#{entity.blowValve}, ligai=#{entity.ligai}, top_cover_fan=#{entity.topCoverFan}, upper_cover_conveying=#{entity.upperCoverConveying}, remark=#{entity.remark}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") PurifiedFillingInfoEntity entity);

    /**
     * 根据id删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " purified_filling_info" +
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
    @Select("select * from purified_filling_info where 1=1 ${where} order by create_time desc")
    Page<PurifiedFillingInfoVM> findEntrysByPage(@Param("where") String where);

}

