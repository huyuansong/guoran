package com.guoran.server.he.production.vmodel;

import com.guoran.server.he.production.model.ManagementAnnualEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @time 2023/2/2315:35
 * @outhor zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementAnnualTotalVM {
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
     * 审核人
     */
    private Date auditTime;
    /**
     * 审核日期
     */
    private String auditBy;
    /**
     * 审核状态
     */
    private Integer auditStatus;

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 是否审核通过
     */
    private Integer isAuditPass;

    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;

    /*
     *年度计划从表实体类
     */
    private List<ManagementAnnualEntity> managementAnnualEntity;
}
