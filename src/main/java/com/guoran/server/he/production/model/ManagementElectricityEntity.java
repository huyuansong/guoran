package com.guoran.server.he.production.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 生产电费记录表
 * </p>
 *
 * @author
 * @table production_management_electricity
 * @create
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementElectricityEntity extends BaseOfConcurrencySafeEntity {
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
    /**
     * 审核时间
     */
    private Date auditTime;

    /*
     *驳回原因
     */
    private String auditRejectReason;

}