package com.guoran.server.lsf.Warehouse.model;


import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 仓库管理-仓库报表
 * </p>
 *
 * @author zhangjx
 * @table warehouse_stock_report
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockReportEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 仓库编码:Ck+4位序号；
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 商品编号:SPBM+6位序号
     */
    private String productCode;
    /**
     * 商品编码名称
     */
    private String productName;
    /**
     * 商品名称
     */
    private Integer productType;
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
    private String innerMeasurement;
    /**
     * 期初库存
     */
    private Integer openingInventory;
    /**
     * 本次生产数量
     */
    private Integer yieldThisTime;
    /**
     * 出库数量
     */
    private Integer salesVolumes;
    /**
     * 调出商品数量
     */
    private Integer outproductNum;
    /**
     * 调入商品数量
     */
    private Integer inproductNum;
    /**
     * 库存数量
     */
    private Integer inventoryQuantity;
    /**
     * 实时库存
     */
    private Integer openingCounts;
    /**
     * 实时期初
     */
    private Integer inventorySurplus;
}
