package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 计件工资表子表岗位工资比例
 * </p>
 *
 * @author zhangjx
 * @table sys_wage_ratio
 * @create 2020-08-21
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WageRatioEntity extends BaseOfConcurrencySafeEntity {
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
     * 商品编码
     */
    private String productCode;
    /**
     * 岗位ID
     */
    private String positionId;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 计件单价（元/件）
     */
    private BigDecimal pieceRates;
    /**
     * 工资比例:%
     */
    private BigDecimal wageRatio;
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
     * 岗位明细
     */
    private String positionDetail;

}