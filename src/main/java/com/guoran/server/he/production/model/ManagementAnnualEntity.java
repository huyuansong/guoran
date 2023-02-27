package com.guoran.server.he.production.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @time 2023/2/2313:49
 * @outhor zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementAnnualEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 年度生产计划表主键ID
     */
    private long id;
    /**
     * 预算年度
     */
    private String budgetYear;
    /**
     * 主题信息
     */
    private String topicInformation;
    /**
     * 审核状态
     */
    private Integer annualProductionAuditStatus;
    /**
     * 审核人
     */
    private String annualProductionReviewer;
    /**
     * 审核时间
     */
    private Date annualProductionAuditDate;
    /**
     * 商品编码
     */
    private String commodityCode;
    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * 商品规格
     */
    private String commoditySpecifications;
    /**
     * 计量单位(内部)
     */
    private String commodityCompany;
    /**
     * 总数量
     */
    private Integer commodityCount;
    /**
     * 总均价
     */
    private BigDecimal totalAveragePrice;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 一月份数量
     */
    private Integer januaryNumber;
    /**
     * 一月份均价
     */
    private BigDecimal januaryAveragePrice;
    /**
     * 一月份金额
     */
    private BigDecimal januaryAmountOfMoney;
    /**
     * 二月份数量
     */
    private Integer februaryNumber;
    /**
     * 二月份均价
     */
    private BigDecimal februaryAveragePrice;
    /**
     * 二月份金额
     */
    private BigDecimal februaryAmountOfMoney;
    /**
     * 三月份数量
     */
    private Integer marchNumber;
    /**
     * 三月份均价
     */
    private BigDecimal marchAveragePrice;
    /**
     * 三月份金额
     */
    private BigDecimal marchAmountOfMoney;
    /**
     * 四月份数量
     */
    private Integer aprilNumber;
    /**
     * 四月份均价
     */
    private BigDecimal aprilAveragePrice;
    /**
     * 四月份金额
     */
    private BigDecimal aprilAmountOfMoney;
    /**
     * 五月份数量
     */
    private Integer mayAmountOfNumber;
    /**
     * 五月份均价
     */
    private BigDecimal mayAveragePrice;
    /**
     * 五月份金额
     */
    private BigDecimal mayAmountOfMoney;
    /**
     * 六月份数量
     */
    private Integer juneNumber;
    /**
     * 六月份均价
     */
    private BigDecimal juneAveragePrice;
    /**
     * 六月份金额
     */
    private BigDecimal juneAmountOfMoney;
    /**
     * 七月份数量
     */
    private Integer julyNumber;
    /**
     * 七月份均价
     */
    private BigDecimal julyAveragePrice;
    /**
     * 七月份金额
     */
    private BigDecimal julyAmountOfMoney;
    /**
     * 八月份数量
     */
    private Integer augustNumber;
    /**
     * 八月份均价
     */
    private BigDecimal augustAveragePrice;
    /**
     * 八月份金额
     */
    private BigDecimal augustAmountOfMoney;
    /**
     * 九月份数量
     */
    private Integer septembertNumber;
    /**
     * 九月份均价
     */
    private BigDecimal septembertAveragePrice;
    /**
     * 九月份金额
     */
    private BigDecimal septembertAmountOfMoney;
    /**
     * 十月份数量
     */
    private Integer octobertNumber;
    /**
     * 十月份均价
     */
    private BigDecimal octobertAveragePrice;
    /**
     * 十月份金额
     */
    private BigDecimal octobertAmountOfMoney;
    /**
     * 十一月份数量
     */
    private Integer novembertNumber;
    /**
     * 十一月份均价
     */
    private BigDecimal novembertAveragePrice;
    /**
     * 十一月份金额
     */
    private BigDecimal novembertAmountOfMoney;
    /**
     * 十二月份数量
     */
    private Integer decembertNumber;
    /**
     * 十二月份均价
     */
    private BigDecimal decembertAveragePrice;
    /**
     * 十二月份金额
     */
    private BigDecimal decembertAmountOfMoney;

    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;

    /*
     *主表id
     */
    private long annualTotalId;

}
