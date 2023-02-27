package com.guoran.server.liu.financial.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WagesVO {

    /**
     * 年月2020—09
     */
    private String yearmonth;

    /**
     * 是否参照
     */
    private Integer isRefer;

    /**
     * 参照月份
     */
    private String lastYearmonth;
    /**
     * 选中的工号
     */
    private String[] jobNumbers;

    /**
     * 基本工资
     */
    private BigDecimal basePay;
    /**
     * 绩效工资
     */
    private BigDecimal meritPay;
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页条数
     */
    private int pageSize;


}
