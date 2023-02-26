package com.guoran.server.sys.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品信息表DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVM {
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
     * 主计量单位 1 件  2 箱
     */
    private Integer mainMeasurement;
    /**
     * 内部计量单位  1 瓶  2 罐  3 桶
     */
    private Integer innerMeasurement;
    /**
     * 产品类型  1 产成品  2 半成品  3 原果
     */
    private Integer productType;
    /**
     * 关联电商编码
     */
    private String innerOrderCode;
    /**
     * 生产渠道  1 外购   2 自产
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
     * /*
     * 并发版本号
     */
    private Integer concurrencyVersion;
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
     * 1:"果酒",
     * 2:"浓缩液",
     * 3:"原浆",
     * 4:"纯净水",
     * 5:"果饮",
     * 6:"鲜果礼品盒",
     * 7:"鲜果电商盒",
     * 8:"浓缩果汁"
     */
    private Integer productCategory;
}