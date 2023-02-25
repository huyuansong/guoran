package com.guoran.server.ma.scattered.repository;

import com.github.pagehelper.Page;
import com.guoran.server.ma.scattered.model.JobInfoEntity;
import com.guoran.server.ma.scattered.vmodel.JobInfoVM;
import org.apache.ibatis.annotations.*;

/**
 * @description: 零工信息数据访问层
 * @author: zhangjx
 * @create: 2020-08-28
 * @Modify By
 */
@Mapper
public interface JobInfoRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from scattered_job_info where id =#{id}")
    JobInfoEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into scattered_job_info (" +
            " `odd_job_code`, `odd_job_name`, `id_card`, `contact_number`, `audit_status`, `audit_by`, `audit_time`,`create_time`, `create_by`) values(" +
            "#{entity.oddJobCode}, #{entity.oddJobName}, #{entity.idCard}, #{entity.contactNumber}, #{entity.auditStatus}, #{entity.auditBy}, #{entity.auditTime}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") JobInfoEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update scattered_job_info set " +
            "odd_job_code=#{entity.oddJobCode}, odd_job_name=#{entity.oddJobName}, id_card=#{entity.idCard}, contact_number=#{entity.contactNumber}, audit_status=#{entity.auditStatus}, audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") JobInfoEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from scattered_job_info where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from scattered_job_info where 1=1 ${where}")
    Page<JobInfoVM> findEntrysByPage(@Param("where") String where);

    /**
     * 审核
     *
     * @param entity
     */

    @Update("update scattered_job_info set " +
            "  audit_status=#{entity.auditStatus}, audit_by=#{entity.auditBy},audit_time=#{entity.auditTime}, update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_reject_reason=#{entity.auditRejectReason} where id = #{entity.id}")
    boolean auditBy(@Param("entity") JobInfoEntity entity);

    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from scattered_job_info where audit_status=2 ")
    int getBacklog();
}
