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
 * 水表信息
 * </p>
 *
 * @author zhangjx
 * @table sys_water_meter
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterMeterEntity extends BaseOfConcurrencySafeEntity {

    private Long id;
    /**
     * 水表编号:GRSB+4位序号
     */
    private String code;
    /**
     * 期初水位
     */
    private BigDecimal initialWaterLevel;
    /**
     * 水表所在详细位置
     */
    private String address;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 驳回原因
     */
    private String dismissReason;
    /**
     * 审核人
     */
    private String auditBy;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 子菜单
     */
    private List<MeterDepartEntity> content;


}