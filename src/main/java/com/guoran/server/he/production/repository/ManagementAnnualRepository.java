package com.guoran.server.he.production.repository;

import com.guoran.server.he.production.model.ManagementAnnualEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @time 2023/2/2413:47
 * @outhor zhou
 */
@Mapper
public interface ManagementAnnualRepository {
    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into production_management_annual (" +
            " `budget_year`, `topic_information`, `annual_production_audit_status`, `annual_production_reviewer`, `annual_production_audit_date`, `commodity_code`, `commodity_name`, `commodity_specifications`, `commodity_company`, `commodity_count`, `total_average_price`, `total_amount`, `january_number`, `january_average_price`, `january_amount_of_money`, `february_number`, `february_average_price`, `february_amount_of_money`, `march_number`, `march_average_price`, `march_amount_of_money`, `april_number`, `april_average_price`, `april_amount_of_money`, `may_amount_of_number`, `may_average_price`, `may_amount_of_money`, `june_number`, `june_average_price`, `june_amount_of_money`, `july_number`, `july_average_price`, `july_amount_of_money`, `august_number`, `august_average_price`, `august_amount_of_money`, `septembert_number`, `septembert_average_price`, `septembert_amount_of_money`, `octobert_number`, `octobert_average_price`, `octobert_amount_of_money`, `novembert_number`, `novembert_average_price`, `novembert_amount_of_money`, `decembert_number`, `decembert_average_price`, `decembert_amount_of_money`,`create_time`, `create_by`,`annual_total_id`) values(" +
            "#{entity.budgetYear}, #{entity.topicInformation}, #{entity.annualProductionAuditStatus}, #{entity.annualProductionReviewer}, #{entity.annualProductionAuditDate}, #{entity.commodityCode}, #{entity.commodityName}, #{entity.commoditySpecifications}, #{entity.commodityCompany}, #{entity.commodityCount}, #{entity.totalAveragePrice}, #{entity.totalAmount}, #{entity.januaryNumber}, #{entity.januaryAveragePrice}, #{entity.januaryAmountOfMoney}, #{entity.februaryNumber}, #{entity.februaryAveragePrice}, #{entity.februaryAmountOfMoney}, #{entity.marchNumber}, #{entity.marchAveragePrice}, #{entity.marchAmountOfMoney}, #{entity.aprilNumber}, #{entity.aprilAveragePrice}, #{entity.aprilAmountOfMoney}, #{entity.mayAmountOfNumber}, #{entity.mayAveragePrice}, #{entity.mayAmountOfMoney}, #{entity.juneNumber}, #{entity.juneAveragePrice}, #{entity.juneAmountOfMoney}, #{entity.julyNumber}, #{entity.julyAveragePrice}, #{entity.julyAmountOfMoney}, #{entity.augustNumber}, #{entity.augustAveragePrice}, #{entity.augustAmountOfMoney}, #{entity.septembertNumber}, #{entity.septembertAveragePrice}, #{entity.septembertAmountOfMoney}, #{entity.octobertNumber}, #{entity.octobertAveragePrice}, #{entity.octobertAmountOfMoney}, #{entity.novembertNumber}, #{entity.novembertAveragePrice}, #{entity.novembertAmountOfMoney}, #{entity.decembertNumber}, #{entity.decembertAveragePrice}, #{entity.decembertAmountOfMoney}, #{entity.createTime},#{entity.createBy},#{entity.annualTotalId})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") ManagementAnnualEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update production_management_annual set " +
            "budget_year=#{entity.budgetYear}, topic_information=#{entity.topicInformation}, annual_production_audit_status=#{entity.annualProductionAuditStatus}, annual_production_reviewer=#{entity.annualProductionReviewer}, annual_production_audit_date=#{entity.annualProductionAuditDate}, commodity_code=#{entity.commodityCode}, commodity_name=#{entity.commodityName}, commodity_specifications=#{entity.commoditySpecifications}, commodity_company=#{entity.commodityCompany}, commodity_count=#{entity.commodityCount}, total_average_price=#{entity.totalAveragePrice}, total_amount=#{entity.totalAmount}, january_number=#{entity.januaryNumber}, january_average_price=#{entity.januaryAveragePrice}, january_amount_of_money=#{entity.januaryAmountOfMoney}, february_number=#{entity.februaryNumber}, february_average_price=#{entity.februaryAveragePrice}, february_amount_of_money=#{entity.februaryAmountOfMoney}, march_number=#{entity.marchNumber}, march_average_price=#{entity.marchAveragePrice}, march_amount_of_money=#{entity.marchAmountOfMoney}, april_number=#{entity.aprilNumber}, april_average_price=#{entity.aprilAveragePrice}, april_amount_of_money=#{entity.aprilAmountOfMoney}, may_amount_of_number=#{entity.mayAmountOfNumber}, may_average_price=#{entity.mayAveragePrice}, may_amount_of_money=#{entity.mayAmountOfMoney}, june_number=#{entity.juneNumber}, june_average_price=#{entity.juneAveragePrice}, june_amount_of_money=#{entity.juneAmountOfMoney}, july_number=#{entity.julyNumber}, july_average_price=#{entity.julyAveragePrice}, july_amount_of_money=#{entity.julyAmountOfMoney}, august_number=#{entity.augustNumber}, august_average_price=#{entity.augustAveragePrice}, august_amount_of_money=#{entity.augustAmountOfMoney}, septembert_number=#{entity.septembertNumber}, septembert_average_price=#{entity.septembertAveragePrice}, septembert_amount_of_money=#{entity.septembertAmountOfMoney}, octobert_number=#{entity.octobertNumber}, octobert_average_price=#{entity.octobertAveragePrice}, octobert_amount_of_money=#{entity.octobertAmountOfMoney}, novembert_number=#{entity.novembertNumber}, novembert_average_price=#{entity.novembertAveragePrice}, novembert_amount_of_money=#{entity.novembertAmountOfMoney}, decembert_number=#{entity.decembertNumber}, decembert_average_price=#{entity.decembertAveragePrice}, decembert_amount_of_money=#{entity.decembertAmountOfMoney}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") ManagementAnnualEntity entity);

    /**
     * 批量删除
     *
     * @param
     */
    @Delete({"<script>" +
            " delete from" +
            " production_management_annual" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    void deleteBanch(@Param(value = "ids") List<Long> ids);

    /**
     * 审核
     */
    @Update("update production_management_annual set " +
            " annual_production_audit_status=#{entity.annualProductionAuditStatus},annual_production_reviewer=#{entity.annualProductionReviewer}, annual_production_audit_date=#{entity.annualProductionAuditDate},update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_reject_reason=#{entity.auditRejectReason} where annual_total_id=#{entity.id}")
    boolean auditBy(@Param("entity") ManagementAnnualEntity entity);

    /**
     * 根据外键删除关联的商品年计划信息
     *
     * @param annualTotalId
     */
    @Delete("delete from production_management_annual where annual_total_id=#{annualTotalId}")
    boolean deleteByoTalId(@Param("annualTotalId") long annualTotalId);

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @Select("select pma.*,pmat.create_time from production_management_annual as pma,production_management_annual_total as pmat WHERE pma.annual_total_id = pmat.id  order by pmat.create_time desc ")
    List<ManagementAnnualEntity> findAllEmpolyee();

}
