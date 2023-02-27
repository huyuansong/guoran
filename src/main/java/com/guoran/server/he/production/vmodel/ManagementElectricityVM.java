package com.guoran.server.he.production.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @time 2023/2/2714:31
 * @outhor zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementElectricityVM {
    /**
     * 电费表id
     */
    private Integer id;
    /**
     * 车间id
     */
    private String departmentId;
    /**
     * 车间名称
     */
    private String departmentName;
    /**
     * 电表编号
     */
    private String meterNumber;
    /**
     * 电表详细位置
     */
    private String detailedLocation;
    /**
     * 抄表时间
     */
    private Date meterReadingTime;
    /**
     * 上次读数
     */
    private BigDecimal lastReading;
    /**
     * 本次读数
     */
    private BigDecimal thisReading;
    /**
     * 增量
     */
    private BigDecimal increment;
    /**
     * 抄表人
     */
    private String meterReader;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 审核人
     */
    private String auditName;
    /**
     * 倍率
     */
    private BigDecimal override;
    /**
     * 用电量
     */
    private BigDecimal electricityConsumption;

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
     * 审核时间
     */
    private Date auditTime;

    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;
}
