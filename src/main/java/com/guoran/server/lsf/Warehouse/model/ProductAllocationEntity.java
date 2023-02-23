package com.guoran.server.lsf.Warehouse.model;


import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 仓库管理-产品调拨
 * </p>
 *
 * @author zhangjx
 * @table warehouse_product_allocation
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAllocationEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 调出仓库编码：CK+4位序号
     */
    private String outwarehouseCode;
    /**
     * 调出仓库名称
     */
    private String outwarehouseName;
    /**
     * 商品编码：SPBM+6位序号;
     */
    private String productCode;
    /**
     * 商品编码名称
     */
    private String productName;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 商品批次号
     */
    private String commodityBatchNumber;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 主计量单位
     */
    private Integer mainMeasurement;
    /**
     * 内部计量单位
     */
    private Integer innerMeasurement;
    /**
     * 调出商品数量
     */
    private Integer outproductNum;
    /**
     * 调拨最大数量
     */
    private Integer maximumQuantity;
    /**
     * 调入仓库编码；Ck+4位序号
     */
    private String inwarehouseCode;
    /**
     * 调入仓库名称
     */
    private String inwarehouseName;
    /**
     * 调入商品数量
     */
    private Integer inproductNum;
    /**
     * 调拨时间
     */
    private Data allocationTime;
    /**
     * 审核状态
     */
    private Integer audiStatus;
    /**
     * 审核人
     */
    private String auditBy;
    /**
     * 审核时间
     */


    private Data auditTime;
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
    /**
     * 操作人
     */
    private String operatorBy;
    /**
     * 操作时间
     */
    private Date operatorTime;
    /**
     * 调拨剩余数量
     */
    private Integer adjustableNumber;
    /**
     * 调拨批次号
     */
    private String assignBatchNumber;
}
