package com.guoran.server.generator.sequence.repository;

import com.github.pagehelper.Page;
import com.guoran.server.generator.sequence.model.SequenceEntity;
import com.guoran.server.generator.sequence.vmodel.SequenceVM;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SequenceRepository {

    /**
     * 返回全部
     *
     * @return
     */
    @Select("select * from generator_sequence ")
    SequenceEntity findAll();

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    @Select("select * from generator_sequence where id = #{id}")
    SequenceEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */
    @Insert("insert into generator_sequence (`code_type`, `seq`, `remark`, `create_by`, `create_time `, `update_by`,`update_time`) values" +
            "(#{entity.codeType},#{entity.seq},#{entity.remark},#{entity.createBy},#{entity.createTime},#{entity.updateBy},#{entity.updateTime})")
    void insert(@Param("entity") SequenceEntity entity);

    /**
     * 更新
     *
     * @param entity
     */
    @Update("update generator_sequence set code_type=#{entity.codeType}, seq=#{entity.seq},remark=#{entity.remark},concurrency_version=concurrency_version+1," +
            "update_by=#{entity.updateBy},update_time=#{entity.updateTime} WHERE id=#{entity.id} and concurrency_version=#{entity.concurrencyVersion}")
    int update(@Param("entity") SequenceEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(@Param("id") long id);

    @Delete("delete from generator_sequence where id=#{id}")

    @Select("select * from generator_sequence where 1=1 and ${where}")
    Page<SequenceVM> findSequencesByPage(@Param("where") String where);

    /**
     * 根据codeType查找
     *
     * @param codeType
     * @return
     */
    @Select("select * from generator_sequence where code_type = #{codeType}")
    SequenceEntity findSequenceByCodeType(@Param("codeType") String codeType);
}
