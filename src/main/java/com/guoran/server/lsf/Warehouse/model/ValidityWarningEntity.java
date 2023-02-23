package com.guoran.server.lsf.Warehouse.model;


import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 仓库管理-有效期预警
 * </p>
 *
 * @author zhangjx
 * @table warehouse_validity_warning
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidityWarningEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 仓库编码:CK+4位序号
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 商品编号:SPBM+6位序号
     */
    private String productCOde;
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
    private Integer innermeasurment;
    /**
     * 商品批次号
     */
    private String batchNumber;
    /**
     * 库存数量
     */
    private Integer inventroyQuantity;
    /**
     * 生产日期
     */
    private Data manufactureDate;
    /**
     * 有效日期
     */
    private Data effectivDate;
    /**
     * 剩余到期天数
     */
    private Long raminingMaturityDays;
    /**
     * 创建人
     */
    private String createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Data updateTime;

}
