package com.guoran.server.he.production.repository;

import com.github.pagehelper.Page;
import com.guoran.server.he.production.model.ManagementAnnualTotalEntity;
import com.guoran.server.he.production.vmodel.ManagementAnnualTotalVM;
import com.guoran.server.he.production.vmodel.ManagementAnnualVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @time 2023/2/2313:58
 * @outhor zhou
 */
@Mapper
public interface ManagementAnnualTotalRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from production_management_annual_total where id =#{id}")
    ManagementAnnualTotalEntity findById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from production_management_annual_total where 1=1 ${where}")
    Page<ManagementAnnualTotalVM> findEntrysByPage(@Param("where") String where);


    /**
     * 根据年度计划id 查询各个商品的年度计划列表
     *
     * @param id
     * @return
     */
    @Select("select * from production_management_annual where  annual_total_id=#{id}")
    List<ManagementAnnualVM> findEntrysPagetotal(@Param("id") long id);


    /**
     * 新增
     */
    @Insert("insert into production_management_annual_total (" +
            " `budget_year`, `topic_information`,`is_biger`, `audit_time`, `audit_by`, `audit_status`,`is_audit_pass`,`create_time`, `create_by`) values(" +
            "#{entity.budgetYear}, #{entity.topicInformation}, #{entity.isBiger}, #{entity.auditTime}, #{entity.auditBy}, #{entity.auditStatus},#{entity.isAuditPass}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") ManagementAnnualTotalEntity entity);


    /**
     * 更新
     *
     * @param entity
     */

    @Update("update production_management_annual_total set " +
            "budget_year=#{entity.budgetYear}, topic_information=#{entity.topicInformation},is_biger=#{entity.isBiger}, audit_time=#{entity.auditTime}, audit_by=#{entity.auditBy}, audit_status=#{entity.auditStatus}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") ManagementAnnualTotalEntity entity);

    /**
     * 审核
     */
    @Update("update production_management_annual_total set " +
            " audit_status=#{entity.auditStatus},is_audit_pass = #{entity.isAuditPass},audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime},update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_reject_reason=#{entity.auditRejectReason} where id=#{entity.id}")
    boolean auditBy(@Param("entity") ManagementAnnualTotalEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from production_management_annual where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 查询年度计划年份是否重复
     */
    @Select("select count(*) from production_management_annual where  budget_year=#{budgetYear}")
    int queryBudgetYearCont(@Param("budgetYear") String budgetYear);
}
