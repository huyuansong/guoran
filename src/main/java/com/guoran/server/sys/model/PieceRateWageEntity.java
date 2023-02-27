package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 计件工资表
 * </p>
 *
 * @author zhangjx
 * @table sys_piece_rate_wage
 * @create 2020-08-21
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceRateWageEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 部门ID
     */
    private String departId;
    /**
     * 部门名称
     */
    private String departName;
    /**
     * 商品编码：SPBM+6位序号
     */
    private String productCode;
    /**
     * 商品编码名称
     */
    private String productName;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 计件单价（元/件）
     */
    private BigDecimal pieceRates;
    /**
     * 单位
     */
    private String mainMeasurement;
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
}