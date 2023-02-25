package com.guoran.server.ma.scattered.repository;

import com.github.pagehelper.Page;
import com.guoran.server.ma.scattered.model.JobFileEntity;
import com.guoran.server.ma.scattered.vmodel.JobFileVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 零工信息管理-附件信息表数据访问层
 * @author: zhangjx
 * @create: 2020-08-28
 * @Modify By
 */
@Mapper
public interface JobFileRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from scattered_job_file where id =#{id}")
    JobFileEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into scattered_job_file (" +
            " `odd_job_code`, `file_type`, `file_name`, `file_url`, `file_size`,`create_time`, `create_by`) values(" +
            "#{entity.oddJobCode}, #{entity.fileType}, #{entity.fileName}, #{entity.fileUrl}, #{entity.fileSize}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") JobFileEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update scattered_job_file set " +
            "odd_job_code=#{entity.oddJobCode}, file_type=#{entity.fileType}, file_name=#{entity.fileName}, file_url=#{entity.fileUrl}, file_size=#{entity.fileSize}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") JobFileEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from scattered_job_file where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 根据id删除
     *
     * @param oddJobCode
     */
    @Delete("delete from scattered_job_file where odd_job_code=#{oddJobCode}")
    boolean deleteByFile(@Param("oddJobCode") String oddJobCode);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from scattered_job_file where 1=1 ${where}")
    Page<JobFileVM> findEntrysByPage(@Param("where") String where);

    /**
     * 分页查询
     *
     * @param oddJobCode
     * @return
     */
    @Select("select * from scattered_job_file where odd_job_code=#{oddJobCode}")
    List<JobFileEntity> findEntrysByPageJobFile(@Param("oddJobCode") String oddJobCode);

    /**
     * 批量插入
     *
     * @param
     */
    @Insert({"<script>" +
            "<foreach collection='jobFileVMS' item='entity' open='' close='' separator=';'>" +
            "insert into scattered_job_file (" +
            " `odd_job_code`, `file_type`, `file_name`, `file_url`, `file_size`,`create_time`, `create_by`) values( " +
            " #{entity.oddJobCode}, #{entity.fileType}, #{entity.fileName}, #{entity.fileUrl}, #{entity.fileSize}, #{entity.createTime},#{entity.createBy})" +
            "</foreach>" +
            "</script>"})
    void insertBanch(@Param(value = "jobFileVMS") List<JobFileEntity> jobFileVMS);

    /**
     * 批量修改
     *
     * @param
     */
    @Update({"<script>" +
            "<foreach collection='jobFileVMS' item='entity' open='' close='' separator=';'>" +
            " update scattered_job_file set " +
            " odd_job_code=#{entity.oddJobCode}, file_type=#{entity.fileType}, file_name=#{entity.fileName}, file_url=#{entity.fileUrl}, file_size=#{entity.fileSize}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} " +
            " where id= #{entity.id} " +
            "</foreach>" +
            "</script>"})
    void updateBanch(@Param(value = "jobFileVMS") List<JobFileEntity> jobFileVMS);

    /**
     * 批量删除
     *
     * @param
     */
    @Delete({"<script>" +
            " delete from scattered_job_file" +
            " where id in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id}" +
            "</foreach>" +
            "</script>"})
    void deleteBanch(@Param(value = "ids") List<Long> list5);
}
