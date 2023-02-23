package com.guoran.server.lsf.Warehouse.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 仓库管理-库存预警
 * </p>
 *
 * @author zhangjx
 * @table warehouse_inventory_warning
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryWarningEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * *商品编码：SPBM+6位序号
     */
    private String productCode;
    /**
     * *仓库编码；CK+4位序号
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 商品编码名称
     */
    private String productName;
    /**
     * 库存状态
     */
    private String tooltipstatus;
    /**
     * 商品类型
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
     * 库存数量
     */
    private Integer inventroyQuantity;
    /**
     * 待出库数量
     */
    private Integer waitOutwarehouseNum;
    /**
     * 待入库数量
     */
    private Integer waitInwarhouseNum;
    /**
     * 余量
     */
    private Integer allowance;
    /**
     * 库存下限
     */
    private Long inventoryFloor;
    /**
     * 库存上限
     */
    private Long inventoryCap;
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

}
