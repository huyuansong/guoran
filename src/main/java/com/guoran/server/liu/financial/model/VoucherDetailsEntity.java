package com.guoran.server.liu.financial.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDetailsEntity extends BaseOfConcurrencySafeEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 借贷方向
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
     * 凭证id
     */
    private long voucherId;
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

}
