package com.guoran.server.liu.financial.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-04
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDetailsVM {
    /**
     * id
     */
    private Long id;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 借贷方向     0借方 1贷方
     */
    private String borrowingDirection;
    /**
     * 科目名称
     */
    private String subject;
    /**
     * 科目编码
     */
    private String subjectCode;
    /**
     * 借方金额
     */
    private BigDecimal debitAmount;
    /**
     * 贷方金额
     */
    private BigDecimal creditAmount;
    /**
     * 主现金流向
     */
    private String mainCashFlow;
    /**
     * 附现金流
     */
    private String deputyCashFlow;
    /**
     * 辅助核算
     */
    private String assistAccounting;
    /**
     * 摘要
     */
    private String remark;
    /**
     * 核算主体
     */
//    private String accountingEntity;
    /**
     * 凭证类型
     */
//    private String documentType;
    /**
     * 凭证编号
     */
//    private String voucherCode;
    /**
     * 抬头文本
     */
//    private String voucherText;
    /**
     * 制单日期：生成凭证日期
     */
//    private Date preparationDate;
    /**
     * 凭证id
     */
    private long voucherId;
    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateBy;
}