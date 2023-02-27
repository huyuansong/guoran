package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.EmployeeEntity;
import com.guoran.server.sys.vmodel.EmployeeVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 员工信息表数据访问层
 * @author: zhangjx
 * @create: 2020-08-19
 * @Modify By
 */
@Mapper
public interface EmployeeRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_employee where id =#{id}")
    EmployeeEntity findById(@Param("id") long id);

    /**
     * 根据userCode查找
     *
     * @param userCode
     * @return
     */
    @Select("select * from sys_employee where job_number =#{userCode}")
    EmployeeEntity findByUserCode(@Param("userCode") String userCode);


    /**
     * 根据部门id查找
     *
     * @param departmentId
     * @return
     */
    @Select("select distinct department_name from sys_employee where department_id =#{departmentId}")
    EmployeeEntity findByCode(@Param("departmentId") String departmentId);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_employee (" +
            " `user_code`, `archives_in_company_id`, `archives_in_company_name`, `department_id`, `department_name`, `position_id`, `position_name`, `user_name`, `id_card`, `job_number`, `mobile`, `audit_status`, `is_delete`,`dismiss_reason`, `binding`,`create_by`, `create_time`, `update_by`, `update_time`,`is_add`,`position_detail`) values(" +
            "#{entity.userCode}, #{entity.archivesInCompanyId}, #{entity.archivesInCompanyName}, #{entity.departmentId}, #{entity.departmentName}, #{entity.positionId}, #{entity.positionName}, #{entity.userName}, #{entity.idCard}, #{entity.jobNumber}, #{entity.mobile}, #{entity.auditStatus}, #{entity.isDelete},#{entity.dismissReason},#{entity.binding}, #{entity.createBy}, #{entity.createTime}, #{entity.updateBy}, #{entity.updateTime},1,#{entity.positionDetail})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") EmployeeEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_employee set " +
            "user_code=#{entity.userCode}, archives_in_company_id=#{entity.archivesInCompanyId}, archives_in_company_name=#{entity.archivesInCompanyName}, department_id=#{entity.departmentId}, department_name=#{entity.departmentName}, position_id=#{entity.positionId}, position_name=#{entity.positionName}, user_name=#{entity.userName}, id_card=#{entity.idCard},position_detail=#{entity.positionDetail}, job_number=#{entity.jobNumber}, mobile=#{entity.mobile}, audit_status=#{entity.auditStatus}, is_delete=#{entity.isDelete}, dismiss_reason=#{entity.dismissReason}, binding=#{entity.binding}, update_by=#{entity.updateBy}, update_time=#{entity.updateTime} where id=#{entity.id}")
    boolean update(@Param("entity") EmployeeEntity entity);

    /**
     * 修改职业绑定登录账号字段
     *
     * @param entity
     * @return
     */
    @Update("update sys_employee set binding=#{entity.binding}, update_by=#{entity.updateBy}, update_time=#{entity.updateTime} where job_number=#{entity.userCode}")
    boolean updateBinding(@Param("entity") EmployeeEntity entity);

    /**
     * 根据id删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_employee" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    boolean deleteById(@Param("ids") String[] Ids);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_employee where 1=1 ${where}")
    Page<EmployeeVM> findEntrysByPage(@Param("where") String where);

    /**
     * 获取所有员工
     */
    @Select("select * from sys_employee")
    List<EmployeeEntity> findAllEmpolyee();

    /**
     * 改变审核状态
     *
     * @param ides state
     */
    @Update({"<script>" +
            " UPDATE" +
            " sys_employee " +
            " SET " +
            " `audit_status` = #{state},`dismiss_reason` = #{dismissReason} " +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    boolean changeReview(@Param("ids") List<String> ides, @Param("state") int state, @Param("dismissReason") String dismissReason);

    /**
     * 批量添加
     *
     * @param employeeEntities
     */
    @Insert({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " insert into " +
            " sys_employee (" +
            " `user_code`, `archives_in_company_id`, `archives_in_company_name`, `department_id`, `department_name`, `position_id`, `position_name`, `user_name`, `id_card`, `job_number`, `mobile`, `audit_status`, `is_delete`,`dismiss_reason`,`binding`, `create_by`, `create_time`, `update_by`, `update_time`) values(" +
            " #{entity.userCode}, #{entity.archivesInCompanyId}, #{entity.archivesInCompanyName}, #{entity.departmentId}, #{entity.departmentName}, #{entity.positionId}, #{entity.positionName}, #{entity.userName}, #{entity.idCard}, #{entity.jobNumber}, #{entity.mobile}, #{entity.auditStatus}, #{entity.isDelete},#{entity.dismissReason},#{entity.binding}, #{entity.createBy}, #{entity.createTime}, #{entity.updateBy}, #{entity.updateTime})" +
            "</foreach>" +
            "</script>"})
    long insertBanch(@Param(value = "entitys") List<EmployeeEntity> employeeEntities);

    /**
     * 批量修改
     *
     * @param employeeEntities2
     * @return
     */
    @Update({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " update  sys_employee" +
            " SET " +
            " user_code=#{entity.userCode}, archives_in_company_id=#{entity.archivesInCompanyId}, archives_in_company_name=#{entity.archivesInCompanyName}, department_id=#{entity.departmentId}, department_name=#{entity.departmentName}, position_id=#{entity.positionId}, position_name=#{entity.positionName}, user_name=#{entity.userName}, id_card=#{entity.idCard}, job_number=#{entity.jobNumber}, mobile=#{entity.mobile}, audit_status=#{entity.auditStatus}, is_delete=#{entity.isDelete}, dismiss_reason=#{entity.dismissReason},  update_by=#{entity.updateBy}, update_time=#{entity.updateTime} where id=#{entity.id} " +
            "</foreach>" +
            "</script>"})
    long updateBanch(@Param(value = "entitys") List<EmployeeEntity> employeeEntities2);

    /**
     * 根据工号查找
     *
     * @param jobNumber
     * @return
     */
    @Select("select * from sys_employee where job_number =#{jobNumber}")
    EmployeeEntity findByJobNumber(@Param("jobNumber") String jobNumber);

}
