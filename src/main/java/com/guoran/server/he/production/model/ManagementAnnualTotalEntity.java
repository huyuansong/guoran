package com.guoran.server.he.production.model;


import com.guoran.server.common.model.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 生产计划主表
 * </p>
 *
 * @author
 * @table production_management_annual_total
 * @create
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementAnnualTotalEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 预算年度
     */
    private String budgetYear;
    /**
     * 预算年度是否大于当前年度
     */
    private Integer isBiger;
    /**
     * 年度主题
     */
    private String topicInformation;
    /**
     * 审核日期
     */
    private Date auditTime;
    /**
     * 审核人
     */
    private String auditBy;
    /**
     * 审核状态
     */
    private Integer auditStatus;


    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;

    /*
     *年度生产计划从表列表
     */
    private List<ManagementAnnualEntity> managementAnnualEntity;
    /**
     * 是否审核通过
     */
    private Integer isAuditPass;
}