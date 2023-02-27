package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zcq
 * @table sys_gas_meter
 * @create 2020-11-27
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasMeterEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 气表编号
     */
    private String code;
    /**
     * 期初读数
     */
    private BigDecimal initialGasLevel;
    /**
     * 气表所在详细位置
     */
    private String address;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
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

}