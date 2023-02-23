package com.guoran.server.shen.sales.vmodel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderMangProductDetailedVm {
    /**
     * 销售商品明细id
     */
    private Integer id;

    /**
     * 电子订单id
     */
    private Integer electronicId;

    /**
     * 销售订单id
     */
    private Integer orderManagementId;
    /**
     * 商品ID
     */
    private Integer commodityId;
    /**
     * 商品编码：SPBM+6位序号
     */
    private String productCode;
    /**
     * 商品批次号
     */
    private String commodityBatchNumber;
    /**
     * 销售数量
     */
    private Integer salesVolumes;

    /**
     * 已出库数量
     */
    private Integer countYes;
    /**
     * 未出库数量
     */
    private Integer countNo;
    /**
     * 销售单价
     */
    private BigDecimal salesUnitPrice;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 规格型号
     */
    private String specifications;
    /**
     * 内部计量单位
     */
    private Integer innerMeasurement;
    /**
     * 根据数数量金额
     */
    private BigDecimal amountOfMoney;
    /**
     * 开票
     */
    private Integer invoice;
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
    /*
     * 更新时间
     * */
    private Date updateTime;
    /**
     * 开具发票类型
     * 1:增值税专用发票/2增值税普通发票/3电子发票
     */
    private Integer billingType;

    /**
     * 搭赠数量
     */
    private Integer takeGift;
    /**
     * 剩余搭赠数量
     */
    private Integer remainingGiftNo;
}
