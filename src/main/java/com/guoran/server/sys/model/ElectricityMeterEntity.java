package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 电表信息
 * </p>
 *
 * @author zhangjx
 * @table sys_electricity_meter
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricityMeterEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 电表编号:GRDB+4位序号
     */
    private String code;
    /**
     * 期初读数
     */
    private BigDecimal initialRead;
    /**
     * 倍率
     */
    private BigDecimal override;
    /**
     * 期初度数=期初度数*倍率
     */
    private BigDecimal initialDegree;
    /**
     * 电表所在详细位置
     */
    private String address;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 审核人
     */
    private String auditBy;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 驳回原因
     */
    private String dismissReason;
    /**
     * 子菜单
     */
    private List<MeterDepartEntity> content;

}