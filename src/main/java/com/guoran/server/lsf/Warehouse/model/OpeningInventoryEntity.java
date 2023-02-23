package com.guoran.server.lsf.Warehouse.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 仓库管理-期初库存
 * </p>
 *
 * @author zhangjx
 * @table warehouse_opening_inventory
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningInventoryEntity extends BaseOfConcurrencySafeEntity {
  private Long id;
  /**
   * 仓库编码：CK+4位序号
   */
  private String warehouseCode;
  /**
   * 仓库名称
   */
  private String warehouseName;
  /**
   * 商品编码：SPBM+6位序号
   */
  private String productCode;
  /**
   * 商品编码名称
   */
  private String productName;
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
   * 期初库存
   */
  private BigDecimal openingInventory;
  /**
   * 实时库存
   */
  private BigDecimal openingCounts;
  /**
   * 期初库存余量
   */
  private BigDecimal inventorySurplus;
  /**
   * 商品批次号
   */
  private String commodityBatchNum;

}