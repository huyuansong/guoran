package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author zhangjx
 * @table sys_product
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
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
     * 主计量单位
     */
    private Integer mainMeasurement;
    /**
     * 内部计量单位
     */
    private Integer innerMeasurement;
    /**
     * 产品类型
     */
    private Integer productType;
    /**
     * 关联电商编码
     */
    private String innerOrderCode;
    /**
     * 生产渠道
     */
    private Integer produceChannel;
    /**
     * 出库单价
     */
    private BigDecimal checkoutPrice;
    /**
     * 预计销售单价
     */
    private BigDecimal salesPrice;
    /**
     * 库存上限
     */
    private Long inventoryUp;
    /**
     * 库存下限
     */
    private Long inventoryDown;
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
     * 启停状态 0停用  1启用
     */
    private String status;
    /**
     * 产品类别
     */
    private Integer productCategory;
}