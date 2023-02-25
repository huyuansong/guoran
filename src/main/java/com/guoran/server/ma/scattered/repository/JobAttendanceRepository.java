package com.guoran.server.ma.scattered.repository;


import com.github.pagehelper.Page;
import com.guoran.server.ma.scattered.model.JobAttendanceEntity;
import com.guoran.server.ma.scattered.vmodel.JobAttendanceVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 零工出勤信息数据访问层
 * @author: zhangjx
 * @create: 2020-08-28
 * @Modify By
 */
@Mapper
public interface JobAttendanceRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from scattered_job_attendance where id =#{id}")
    JobAttendanceEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into scattered_job_attendance (" +
            " `odd_job_name`, `id_card`, `hire_department_id`, `hire_department_name`, `hire_content`, `attendance_beginTime`, `attendance_endtTime`, `attendance_days`, `day_wages`, `attendance_totle_wages`, `audit_status`, `audit_by`, `audit_time`,`create_time`, `create_by`) values(" +
            "#{entity.oddJobName}, #{entity.idCard}, #{entity.hireDepartmentId}, #{entity.hireDepartmentName}, #{entity.hireContent}, #{entity.attendanceBegintime}, #{entity.attendanceEndttime}, #{entity.attendanceDays}, #{entity.dayWages}, #{entity.attendanceTotleWages}, #{entity.auditStatus}, #{entity.auditBy}, #{entity.auditTime}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") JobAttendanceEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update scattered_job_attendance set " +
            "odd_job_name=#{entity.oddJobName}, id_card=#{entity.idCard}, hire_department_id=#{entity.hireDepartmentId}, hire_department_name=#{entity.hireDepartmentName}, hire_content=#{entity.hireContent}, attendance_beginTime=#{entity.attendanceBegintime}, attendance_endtTime=#{entity.attendanceEndttime}, attendance_days=#{entity.attendanceDays}, day_wages=#{entity.dayWages}, attendance_totle_wages=#{entity.attendanceTotleWages}, audit_status=#{entity.auditStatus}, audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") JobAttendanceEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from scattered_job_attendance where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from scattered_job_attendance where 1=1 ${where}")
    Page<JobAttendanceVM> findEntrysByPage(@Param("where") String where);


    /**
     * 审核
     *
     * @param entity
     */

    @Update("update scattered_job_attendance set " +
            "  audit_status=#{entity.auditStatus}, audit_by=#{entity.auditBy},audit_time=#{entity.auditTime}, update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_reject_reason=#{entity.auditRejectReason} where id = #{entity.id}")
    boolean auditBy(@Param("entity") JobAttendanceEntity entity);

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @Select("select * from scattered_job_attendance order by create_time desc ")
    List<JobAttendanceEntity> findAllEmpolyee();


    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from scattered_job_attendance where audit_status=2")
    int getBacklog();

}
