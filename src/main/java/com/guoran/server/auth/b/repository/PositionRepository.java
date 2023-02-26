package com.guoran.server.auth.b.repository;

import com.github.pagehelper.Page;
import com.guoran.server.auth.model.PositionEntity;
import com.guoran.server.auth.vmodel.PositionVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 数据访问层
 * @author: zhangjx
 * @create: 2020-09-10
 * @Modify By
 */
@Mapper
public interface PositionRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from auth_position where id =#{id}")
    PositionEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into auth_position (" +
            " `position_code`, `position_name`, `department_code`, `department_name`,`create_time`, `create_by`,`is_add`,`position_detail`) values(" +
            "#{entity.positionCode}, #{entity.positionName}, #{entity.departmentCode}, #{entity.departmentName}, #{entity.createTime},#{entity.createBy},1,#{entity.positionDetail})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") PositionEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update auth_position set " +
            "position_code=#{entity.positionCode}, position_name=#{entity.positionName}, department_code=#{entity.departmentCode}, department_name=#{entity.departmentName},position_detail=#{entity.positionDetail},update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") PositionEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from auth_position where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from auth_position where 1=1 ${where}")
    Page<PositionVM> findEntrysByPage(@Param("where") String where);

    /**
     * 批量添加
     */
    @Insert({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " insert into " +
            " auth_position (" +
            " `position_code`, `position_name`, `department_code`, `department_name`,`create_time`, `create_by`) values(" +
            "#{entity.positionCode}, #{entity.positionName}, #{entity.departmentCode}, #{entity.departmentName}, #{entity.createTime},#{entity.createBy})" +
            "</foreach>" +
            "</script>"})
    void insertBanch(@Param(value = "entitys") List<PositionEntity> positionEntities);
}
