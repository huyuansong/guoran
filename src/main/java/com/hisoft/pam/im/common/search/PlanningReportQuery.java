package com.hisoft.pam.im.common.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanningReportQuery implements Serializable {

    /**
     * 预算年度
     */
    private String budgetYear;
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
     * 季度信息
     */
    private String quarter;
    /*
     *查询时间
     */
    private String queryTime;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 页码
     */
    private int pageNum;
    /**
     *
     */
    private int pageSize;
}
